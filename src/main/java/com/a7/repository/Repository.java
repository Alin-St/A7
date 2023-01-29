package com.a7.repository;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.programState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {

    private List<ProgramState> _programStates = new ArrayList<>();
    private final String _logFilePath;

    public Repository(String logFilePath) {
        _logFilePath = logFilePath;
    }

    public Repository(String logFilePath, ProgramState initialProgram) {
        this(logFilePath);
        _programStates.add(initialProgram);
    }

    @Override
    public List<ProgramState> getProgramList() { return _programStates; }

    @Override
    public void setProgramList(List<ProgramState> programStates) { _programStates = programStates; }

    @Override
    public void logProgramState(ProgramState program, String prompt) throws InterpreterException {
        try {
            var fw = new FileWriter(_logFilePath, true);
            var bw = new BufferedWriter(fw);
            var pw = new PrintWriter(bw);

            pw.println(prompt == null ? "Program state:" : prompt);
            pw.println(program.toString().indent(4));

            pw.close();
            bw.close();
            fw.close();
        } catch (IOException e) {
            throw new InterpreterException("IOException: " + e.getMessage());
        }
    }
}
