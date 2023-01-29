package com.a7.controller;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.garbageCollector.ConservativeGarbageCollector;
import com.a7.model.programState.ProgramState;
import com.a7.repository.IRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private final IRepository _repository;
    private boolean _displayFlag;
    private ExecutorService _executor;

    public Controller(IRepository repository) {
        _repository = repository;
    }

    public IRepository getRepository() { return _repository; }

    public boolean getDisplayFlag() { return _displayFlag; }

    public void setDisplayFlag(boolean value) { _displayFlag = value; }

    public void oneStepForAllPrograms(List<ProgramState> programStates) throws InterruptedException {
        // Log program states before execution.
        programStates.forEach(p -> {
            try { _repository.logProgramState(p, "Program state before execution (id " + p.getId() + "):"); }
            catch (InterpreterException e) { e.printStackTrace(); }
        });

        // Prepare the list of callables.
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .toList();

        // Start the execution of callables (returns the list of new threads).
        List<ProgramState> newPrograms = _executor.invokeAll(callList).stream()
                .map(future -> {
                    try { return future.get(); }
                    catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        // Add the new programs to the list.
        programStates.addAll(newPrograms);

        // Log program states after execution.
        programStates.forEach(p -> {
            try { _repository.logProgramState(p, "Program state after execution (id " + p.getId() + "):"); }
            catch (InterpreterException e) { e.printStackTrace(); }
        });

        // Save current program list to com.example.a7.repository.
        _repository.setProgramList(programStates);
    }

    public void allSteps() throws InterpreterException, InterruptedException {

        _executor = Executors.newFixedThreadPool(2);

        // Remove completed programs.
        List<ProgramState> programStates = removeCompletedPrograms(_repository.getProgramList());

        // Execution loop.
        while (programStates.size() > 0) {
            // Garbage collector.
            var newHeap = ConservativeGarbageCollector.run(programStates.get(0).getHeapTable(),
                    programStates.stream().map(ProgramState::getSymbolTable).toList());
            programStates.forEach(p -> p.setHeapTable(newHeap));

            // One-step execution.
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrograms(_repository.getProgramList());
        }
        _executor.shutdown();

        // The com.example.a7.repository should contain at least one completed program (its list is not empty).

        _repository.setProgramList(programStates);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream().filter(ProgramState::isNotCompleted).collect(Collectors.toList());
    }
}
