package org.abdz.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
//        List used to store our table data.
        List<TableColumn> tableColumn = new ArrayList<>();

        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox searchHbox = new HBox(5);
        HBox navigationHbox = new HBox(20);

//        Searching Components
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

        Label statusLabel = new Label();

//        Trigger button when the search button is clicked
        searchBtn.setOnAction(e -> {
//            Check if the textfield is empty
            if (!trainerIdField.getText().isEmpty()) {
                try {
//                    Check if there is any characters other than whole numbers
                    if (!trainerIdField.getText().matches("[0-9]+")) {
//                        Set the text for the label
                        statusLabel.setText("IDs can only be integers!");
//                        Set red colour to our font
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }

//                    Write to our socket which is connected to our server
                    Main.outputStream.writeUTF("listpt=" + trainerIdField.getText());
//                    Flush the socket so the message is sent immediately
                    Main.outputStream.flush();
//                    Load the data for our new table
                    loadData(tableView);
//                    Clear the trainer id text field so that is becomes empty
                    trainerIdField.clear();
//                    Remove the text for our label
                    statusLabel.setText("");
                    return;
                } catch (IOException ex) {
                    System.err.println("Can't write to server.");
                    ex.printStackTrace();
                }
            }

//            Check if the textfield is empty
            if (!clientIdField.getText().isEmpty()) {
                try {
//                    Check if there is any characters other than whole numbers
                    if (!clientIdField.getText().matches("[0-9]+")) {
//                        Set the text for the label
                        statusLabel.setText("IDs can only be integers!");
//                        Set red colour to our font
                        statusLabel.setStyle("-fx-mid-text-color: red;");
                        return;
                    }
//                Write to the stream to send a message to our server
                    Main.outputStream.writeUTF("listclient=" + clientIdField.getText());
//                Flush the stream so that the message is sent over immediately
                    Main.outputStream.flush();
//                Load the data for our new table
                    loadData(tableView);
//                    Clear the textfield so it's empty
                    clientIdField.clear();
//                    Remove the text for our label
                    statusLabel.setText("");
                    return;
                } catch (IOException ex) {
                    System.err.println("Can't write to server.");
                    ex.printStackTrace();
                }
            }

//            Check if the date textfields have got a value by checking if it's not null
            if (fromDateField.getDateValue() != null && toDateField.getDateValue() != null) {
                try {
//                Write to the stream to send a message to our server
                    Main.outputStream.writeUTF("listdate=" + fromDateField.getDateValue() + "=" + toDateField.getDateValue());
//                Flush the stream so that the message is sent over immediately
                    Main.outputStream.flush();
//                Load the data for our new table
                    loadData(tableView);
                    fromDateField.getEditor().clear();
                    toDateField.getEditor().clear();
                } catch (IOException ex) {
                    System.err.println("Can't write to server.");
                    ex.printStackTrace();
                }
            }

//            Clear textfields ready for next input
            trainerIdField.clear();
            clientIdField.clear();
            fromDateField.getEditor().clear();
            toDateField.getEditor().clear();
        });

//        A for loop iterate our TABLE_HEADING array
        for (int i = 0; i < TABLE_HEADINGS.length; i++) {
//            Add each new column to our tableColumn list
            tableColumn.add(new TableColumn(TABLE_HEADINGS[i]));
//            Set a value factory on our column
            tableColumn.get(i).setCellValueFactory(new PropertyValueFactory<>(OBSERVABLE_STRINGS[i]));
//            Finally add the new column to our tableView to display as a table for the user
            tableView.getColumns().add(tableColumn.get(i));
        }

//        Add our components to our containers for display
        searchHbox.getChildren().addAll(trainerIdField, clientIdField, fromDateField, toDateField, searchBtn);
        navigationHbox.getChildren().add(sceneUtils.setBackButton(this));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, searchHbox, tableView);
    }

    /**
     * Get the data from our server and store it in a list
     *
     * @param tv table view object that creates a table
     */
    private void loadData(TableView tv) {
//      Get all the bookings that we have received from the server
        getBookings();
//      Populate the new data on the table
        tv.setItems(getTableData());
    }

    /**
     * Gets the object from our server and returns it as a map for modification
     *
     * @return a map list of the bookings
     */
    private Map<Integer, ArrayList<String>> getBookings() {
        try {
//            Store our object from the server into an object
            Object dataObject = Main.inputStream.readObject();
//            Assign map to our new object
            bookings = (Map<Integer, ArrayList<String>>) dataObject;
//            Return the newly create Map object
            return bookings;

        } catch (IOException e) {
            System.err.println("Can't fetch bookings from server.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Can't fetch bookings from server.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method is used to aid in populating our table
     *
     * @return an observable list for our booking
     */
    private ObservableList<Booking> getTableData() {
//        Return a collection, using observable array list, using our Booking class.
//        We iterate over our list using stream, with the entry set to get the values.
        return FXCollections.observableArrayList(bookings.entrySet().stream().map(
                map -> new Booking(
                        map.getKey(),
                        Integer.parseInt(map.getValue().get(0)),
                        Integer.parseInt(map.getValue().get(1)),
                        map.getValue().get(2),
                        Integer.parseInt(map.getValue().get(3)),
                        map.getValue().get(4),
                        map.getValue().get(5),
                        map.getValue().get(6),
                        map.getValue().get(7),
                        map.getValue().get(8)))
                .collect(Collectors.toList()));
    }
}
