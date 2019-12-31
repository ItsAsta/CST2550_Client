package org.abdz.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abdz.cst2550.sample.Main;
import org.abdz.utils.Booking;
import org.abdz.utils.DateTimePicker;
import org.abdz.utils.SceneUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingSearchScene extends BaseScene {
    public BookingSearchScene(String title) {
        super(title);
    }


    private SceneUtils sceneUtils = new SceneUtils();
    private Map<Integer, ArrayList<String>> bookings = new HashMap<>();

    private final String[] TABLE_HEADINGS = {"Booking ID", "Trainer ID", "Client ID", "Date Time", "Duration",
            "Client First Name", "Client Last Name", "Client D.O.B",
            "Trainer First Name", "Trainer Last Name"};
    private final String[] OBSERVABLE_STRINGS = {"BookingId", "TrainerId", "ClientId", "DateTime", "Duration",
            "ClientFirstName", "ClientLastName", "ClientDob",
            "TrainerFirstName", "TrainerLastName"};

    @Override
    public void fillScene(BorderPane pane) {
        TableView tableView = new TableView();
        List<TableColumn> tableColumn = new ArrayList<>();

        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox searchHbox = new HBox(5);
        HBox navigationHbox = new HBox(20);

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        navigationHbox.getChildren().add(backBtn);

        backBtn.setOnAction(e -> {
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
            hide();
        });

        TextField trainerIdField = new TextField();
        trainerIdField.setPromptText("Trainer ID");

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Client ID");

        DateTimePicker fromDateField = new DateTimePicker();
        fromDateField.setPromptText("From Date");
        fromDateField.setFormat("yyyy-MM-dd");

        DateTimePicker toDateField = new DateTimePicker();
        toDateField.setFormat("yyyy-MM-dd");
        toDateField.setPromptText("To Date");

        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(100);

        searchBtn.setOnAction(e -> {
            if (!trainerIdField.getText().isEmpty()) {
                try {
                    Main.outputStream.writeUTF("listpt=" + trainerIdField.getText());
                    Main.outputStream.flush();
                    loadData(tableView);
                    trainerIdField.clear();
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (!clientIdField.getText().isEmpty()) {
                try {
                    Main.outputStream.writeUTF("listclient=" + clientIdField.getText());
                    Main.outputStream.flush();
                    loadData(tableView);
                    clientIdField.clear();
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if (fromDateField.getDateValue() != null && toDateField.getDateValue() != null) {
                try {
                    Main.outputStream.writeUTF("listdate=" + fromDateField.getDateValue() + "=" + toDateField.getDateValue());
                    Main.outputStream.flush();
                    loadData(tableView);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            trainerIdField.clear();
            clientIdField.clear();
        });

        for (int i = 0; i < TABLE_HEADINGS.length; i++) {
            tableColumn.add(new TableColumn(TABLE_HEADINGS[i]));
            tableColumn.get(i).setCellValueFactory(new PropertyValueFactory<>(OBSERVABLE_STRINGS[i]));
            tableView.getColumns().add(tableColumn.get(i));
        }

        searchHbox.getChildren().addAll(trainerIdField, clientIdField, fromDateField, toDateField, searchBtn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, searchHbox, tableView);
    }

    private void loadData(TableView tv) throws IOException {
        getBookings();
        tv.setItems(getTableData());
    }

    private Map<Integer, ArrayList<String>> getBookings() {
        try {
            Object dataObject = Main.inputStream.readObject();

            bookings = (Map<Integer, ArrayList<String>>) dataObject;
            System.out.println(bookings.size());

            return bookings;

        } catch (IOException e) {
            System.out.println("Can't fetch bookings from server.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ObservableList<Booking> getTableData() {

        return FXCollections.observableArrayList(bookings.entrySet().stream().map(
                map -> new Booking(
                        map.getKey(),
                        Integer.parseInt(map.getValue().get(0)),
                        Integer.parseInt(map.getValue().get(1)),
                        map.getValue().get(2),
                        Float.parseFloat(map.getValue().get(3)),
                        map.getValue().get(4),
                        map.getValue().get(5),
                        map.getValue().get(6),
                        map.getValue().get(7),
                        map.getValue().get(8)))
                .collect(Collectors.toList()));
    }
}
