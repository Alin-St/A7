package com.a7;

import com.a7.controller.Controller;
import com.a7.model.exceptions.InterpreterException;
import com.a7.model.expressions.*;
import com.a7.model.programState.ProgramState;
import com.a7.model.statements.*;
import com.a7.model.types.*;
import com.a7.model.utility.MyDictionary;
import com.a7.model.values.BoolValue;
import com.a7.model.values.IntValue;
import com.a7.model.values.StringValue;
import com.a7.repository.IRepository;
import com.a7.repository.Repository;

import java.util.ArrayList;

public class Interpreter {
    @SuppressWarnings({"DuplicatedCode", "SpellCheckingInspection"})
    static ArrayList<IStatement> getStatements() {
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", IntType.get()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", IntType.get()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", IntType.get()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ArithmeticExpression(
                                        new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)),
                                                ArithmeticOperator.MULTIPLICATION
                                        ),
                                        ArithmeticOperator.ADDITION
                                )),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)),
                                                ArithmeticOperator.ADDITION
                                        )),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );

        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", BoolType.get()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", IntType.get()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(
                                        new ConditionalStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", StringType.get()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenFileReaderStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", IntType.get()),
                                        new CompoundStatement(
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new PrintStatement(new VariableExpression("varc"))
                                                ),
                                                new CompoundStatement(
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new PrintStatement(new VariableExpression("varc"))
                                                        ),
                                                        new CloseFileReaderStatement(new VariableExpression("varf"))
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement ex5 = new CompoundStatement(
                new CompoundStatement(
                        new PrintStatement(new RelationalExpression(
                                new ValueExpression(new IntValue(4)),
                                new ValueExpression(new IntValue(4)),
                                RelationalOperator.NOT_EQUALS
                        )),
                        new PrintStatement(new RelationalExpression(
                                new ValueExpression(new IntValue(4)),
                                new ValueExpression(new IntValue(5)),
                                RelationalOperator.NOT_EQUALS
                        ))
                ),
                new CompoundStatement(
                        new PrintStatement(new RelationalExpression(
                                new ValueExpression(new IntValue(4)),
                                new ValueExpression(new IntValue(4)),
                                RelationalOperator.LESS_THAN
                        )),
                        new PrintStatement(new RelationalExpression(
                                new ValueExpression(new IntValue(4)),
                                new ValueExpression(new IntValue(5)),
                                RelationalOperator.LESS_THAN
                        ))
                )
        );

        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v", IntType.get()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperator.GREATER_THAN
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1)),
                                                        ArithmeticOperator.SUBTRACTION
                                                ))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(IntType.get())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a",
                                        new ReferenceType(new ReferenceType(IntType.get()))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v",
                                                        new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(
                                                        new PrintStatement(new HeapReadingExpression(
                                                                new HeapReadingExpression(
                                                                        new VariableExpression("a")))),
                                                        new AssignmentStatement("a", new ValueExpression(
                                                                new ReferenceType(new ReferenceType(IntType.get()))
                                                                        .defaultValue()))
                                                )
                                        )
                                )
                        )
                )
        );

        IStatement ex8 = new CompoundStatement(
                new CompoundStatement(
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", IntType.get()),
                                new VariableDeclarationStatement("a", new ReferenceType(IntType.get()))
                        ),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new HeapAllocationStatement("a", new ValueExpression(new IntValue(22)))
                        )
                ),
                new CompoundStatement(
                        new ForkStatement(
                                new CompoundStatement(
                                        new HeapWritingStatement("a",
                                                new ValueExpression(new IntValue(30))),
                                        new CompoundStatement(
                                                new AssignmentStatement("v",
                                                        new ValueExpression(new IntValue(32))),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadingExpression(
                                                                new VariableExpression("a")))
                                                )
                                        )
                                )
                        ),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                        )
                )
        );

        IStatement ex9 = new CompoundStatement(
                new CompoundStatement(
                        new VariableDeclarationStatement("n", StringType.get()),
                        new WhileStatement(
                                new ValueExpression(new BoolValue(false)),
                                new VariableDeclarationStatement("n", IntType.get())
                        )
                ),
                new AssignmentStatement(
                        "n",
                        new ArithmeticExpression(
                                new ValueExpression(new IntValue(1)),
                                new ValueExpression(new IntValue(2)),
                                ArithmeticOperator.ADDITION
                        )
                )
        );

        IStatement ex10 = new PrintStatement(
                new LogicalExpression(
                        new ArithmeticExpression(
                                new ValueExpression(new IntValue(1)),
                                new ValueExpression(new IntValue(1)),
                                ArithmeticOperator.ADDITION
                        ),
                        new ValueExpression(new IntValue(1)),
                        LogicOperator.AND
                )
        );

        IStatement ex11 = new CompoundStatement(
                new VariableDeclarationStatement("ptr", new ReferenceType(IntType.get())),
                new CompoundStatement(
                        new HeapAllocationStatement("ptr", new ValueExpression(new IntValue(10))),
                        new HeapWritingStatement("ptr", new ValueExpression(new StringValue("11")))
                )
        );

        var result = new ArrayList<IStatement>();
        result.add(ex1);
        result.add(ex2);
        result.add(ex3);
        result.add(ex4);
        result.add(ex5);
        result.add(ex6);
        result.add(ex7);
        result.add(ex8);
        result.add(ex9);
        result.add(ex10);
        result.add(ex11);
        return result;
    }

    public static ArrayList<Controller> getControllers() {
        var mainStatements = getStatements();
        var result = new ArrayList<Controller>();

        for (int i = 0; i < mainStatements.size(); ++i) {
            var mainStatement = mainStatements.get(i);

            try {
                mainStatement.typeCheck(new MyDictionary<>(String.class, IType.class));
            }
            catch (InterpreterException ex) {
                System.out.println("Program " + i + " failed type check with the message: " + ex.getMessage());
                System.out.println(mainStatement.toString().indent(4));
                continue;
            }

            ProgramState programState = new ProgramState(mainStatement);
            IRepository repository = new Repository("log" + i + ".txt");
            repository.getProgramList().add(programState);
            Controller controller = new Controller(repository);
            result.add(controller);
        }

        return result;
    }
}
