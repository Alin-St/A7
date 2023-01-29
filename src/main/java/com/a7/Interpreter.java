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
import com.a7.view.TextMenu;
import com.a7.view.commands.RunExample;

import java.util.ArrayList;

public class Interpreter {

    static void addProgram(IStatement mainStatement, TextMenu menu, int index)
    {
        try {
            mainStatement.typeCheck(new MyDictionary<>(String.class, IType.class));
        }
        catch (InterpreterException ex) {
            System.out.println("Program " + index + " failed type check with the message: " + ex.getMessage());
            System.out.println(mainStatement.toString().indent(4));
            return;
        }

        ProgramState programState = new ProgramState(mainStatement);
        IRepository repository = new Repository("log" + index + ".txt");
        repository.getProgramList().add(programState);
        Controller controller = new Controller(repository);
        menu.addCommand(new RunExample(Integer.toString(index), mainStatement.toString(), controller));
    }

    static void addProgram(IStatement mainStatement, ArrayList<Controller> controllers, int index) {
        try {
            mainStatement.typeCheck(new MyDictionary<>(String.class, IType.class));
        }
        catch (InterpreterException ex) {
            System.out.println("Program " + index + " failed type check with the message: " + ex.getMessage());
            System.out.println(mainStatement.toString().indent(4));
            return;
        }

        ProgramState programState = new ProgramState(mainStatement);
        IRepository repository = new Repository("log" + index + ".txt");
        repository.getProgramList().add(programState);
        Controller controller = new Controller(repository);
        controllers.add(controller);
    }

    @SuppressWarnings({"DuplicatedCode", "SpellCheckingInspection"})
    public static void main(String[] args) {

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

        // var menu = new TextMenu();
        // menu.addCommand(new ExitCommand("0", "exit"));
        var menu = new ArrayList<Controller>();
        addProgram(ex1, menu, 1);
        addProgram(ex2, menu, 2);
        addProgram(ex3, menu, 3);
        addProgram(ex4, menu, 4);
        addProgram(ex5, menu, 5);
        addProgram(ex6, menu, 6);
        addProgram(ex7, menu, 7);
        addProgram(ex8, menu, 8);
        addProgram(ex9, menu, 9);
        addProgram(ex10, menu, 10);
        addProgram(ex11, menu, 11);
        // menu.show();

        HelloApplication.main(menu);
    }
}
