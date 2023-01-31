package com.a7.gui;

import com.a7.controller.Controller;
import com.a7.model.exceptions.AdtException;
import com.a7.model.garbageCollector.ConservativeGarbageCollector;
import com.a7.model.programState.ProgramState;
import com.a7.model.statements.IStatement;
import com.a7.model.values.IValue;
import com.a7.model.values.StringValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class View2Controller {
    @FXML
    public TextField cntPrgStatesTF;
    @FXML
    public TableView<Pair<Integer, IValue>> heapTableTV;
    @FXML
    public TableColumn<Pair<Integer, IValue>, String> heapTableAddressColumn;
    @FXML
    public TableColumn<Pair<Integer, IValue>, String> heapTableValueColumn;
    @FXML
    public ListView<IValue> outputLV;
    @FXML
    public ListView<StringValue> fileTableLV;
    @FXML
    public ListView<Integer> programStateIdsLV;
    @FXML
    public TableView<Pair<String, IValue>> symbolTableTV;
    @FXML
    public TableColumn<Pair<String, IValue>, String> symbolTableVarNameCol;
    @FXML
    public TableColumn<Pair<String, IValue>, String> symbolTableValueCol;
    @FXML
    public ListView<IStatement> executionStackLV;

    private Controller _controller;
    private ExecutorService _executor;

    public void initData(Controller controller) {
        _controller = controller;
        _executor = Executors.newFixedThreadPool(2);

        heapTableAddressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        heapTableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        symbolTableVarNameCol.setCellValueFactory(new PropertyValueFactory<>("key"));
        symbolTableValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        populateData();

        programStateIdsLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                populateCurrentProgram();
            }
        });
    }

    public void runOneStep() {
        try {
            var programStates = _controller.removeCompletedPrograms(_controller.getRepository().getProgramList());
                _controller.oneStepForAllPrograms(programStates, _executor);

            if (!programStates.isEmpty()) {
                // Garbage collector.
                var newHeap = ConservativeGarbageCollector.run(programStates.get(0).getHeapTable(),
                        programStates.stream().map(ProgramState::getSymbolTable).toList());
                programStates.forEach(p -> p.setHeapTable(newHeap));
            }

            populateData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        _executor.shutdown();
    }

    void populateData() {
        cntPrgStatesTF.setText(Integer.toString(_controller.getRepository().getProgramList().size()));

        heapTableTV.getItems().clear();
        if (_controller.getRepository().getProgramList().size() > 0)
            for (var pair : _controller.getRepository().getProgramList().get(0).getHeapTable().toArrayList())
                heapTableTV.getItems().add(new Pair<>(pair.getKey(), pair.getValue()));

        outputLV.getItems().clear();
        if (_controller.getRepository().getProgramList().size() > 0)
            for (var output : _controller.getRepository().getProgramList().get(0).getOutputStructure().toArrayList())
                outputLV.getItems().add(output);

        fileTableLV.getItems().clear();
        if (_controller.getRepository().getProgramList().size() > 0)
            for (var pair : _controller.getRepository().getProgramList().get(0).getFileTable().toArrayList())
                fileTableLV.getItems().add(pair.getKey());

        int selPrgInd = programStateIdsLV.getSelectionModel().getSelectedIndex();
        int prgId = selPrgInd >= 0 ? programStateIdsLV.getItems().get(selPrgInd) : -1;

        programStateIdsLV.getItems().clear();
        for (var prg : _controller.getRepository().getProgramList())
            programStateIdsLV.getItems().add(prg.getId());

        programStateIdsLV.getSelectionModel().select(programStateIdsLV.getItems().indexOf(prgId));

        populateCurrentProgram();
    }

    void populateCurrentProgram() {
        int selPrgInd = programStateIdsLV.getSelectionModel().getSelectedIndex();
        int prgId = selPrgInd >= 0 ? programStateIdsLV.getItems().get(selPrgInd) : -1;
        ProgramState prg = _controller.getRepository().getProgramList().stream()
                .filter(p -> p.getId() == prgId).findFirst().orElse(null);

        symbolTableTV.getItems().clear();
        if (prg != null)
            for (var pair : prg.getSymbolTable().toArrayList())
                symbolTableTV.getItems().add(new Pair<>(pair.getKey(), pair.getValue()));

        executionStackLV.getItems().clear();
        if (prg != null) {
            try {
                for (var statement : prg.getExecutionStack().toArrayList())
                    executionStackLV.getItems().add(statement);
            } catch (AdtException e) {
                e.printStackTrace();
            }
        }
    }
}
