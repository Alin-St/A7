package com.a7.repository;

import com.a7.model.programState.ProgramState;
import com.a7.model.exceptions.InterpreterException;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> programStates);
    void logProgramState(ProgramState program, String prompt) throws InterpreterException;
}
