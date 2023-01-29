package com.a7.model.statements;

import com.a7.model.exceptions.InterpreterException;
import com.a7.model.exceptions.StatementExecutionException;
import com.a7.model.expressions.IExpression;
import com.a7.model.programState.ProgramState;
import com.a7.model.types.IType;
import com.a7.model.types.StringType;
import com.a7.model.utility.MyIDictionary;
import com.a7.model.values.StringValue;

import java.io.IOException;

public class CloseFileReaderStatement implements IStatement {

    private final IExpression _filename;

    public CloseFileReaderStatement(IExpression filename) {
        _filename = filename;
    }

    @Override
    public String toString() {
        return "closeRFile(" + _filename.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        var symbolTable = state.getSymbolTable();
        var fileTable = state.getFileTable();
        var fnv = _filename.evaluate(symbolTable, state.getHeapTable());

        if (!fnv.getType().equals(StringType.get()))
            throw new StatementExecutionException("Argument of a close file reader is not a string.");

        StringValue fnsv = (StringValue)fnv;
        if (!fileTable.containsKey(fnsv))
            throw new StatementExecutionException("The specified file has not been opened.");

        var br = fileTable.get(fnsv);

        try {
            br.close();
        } catch (IOException e) {
            throw new StatementExecutionException("IOException: " + e.getMessage());
        }

        fileTable.remove(fnsv);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileReaderStatement(_filename.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws InterpreterException {
        if (!_filename.typeCheck(typeEnv).equals(StringType.get()))
            throw new InterpreterException("Filename is not a string.");
        return typeEnv;
    }
}
