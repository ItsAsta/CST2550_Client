package org.abdz.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import org.abdz.events.Booking;
import org.abdz.events.DateTimePicker;
import org.abdz.utils.SceneUtils;
import org.abdz.utils.ServerUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingScene extends BaseScene {
    public BookingScene(String title) {
        super(title);
    }

    private final String[] COLUMN_STRINGS = {"Booking ID", "Trainer ID", "Client ID", "Date Time", "Duration",
            "Client First Name", "Client Last Name", "Client D.O.B",
            "Trainer First Name", "Trainer Last Name"};
    private final String[] OBSERVABLE_STRINGS = {"BookingId", "TrainerId", "ClientId", "DateTime", "Duration",
            "ClientFirstName", "ClientLastName", "ClientDob",
            "TrainerFirstName", "TrainerLastName"};

    private ObjectInputStream objectInput;
    private BufferedWriter output;

    private List<TextField> textFieldFilters = new ArrayList<>();

    private SceneUtils sceneUtils = new SceneUtils();

    @Override
    public void fillScene(BorderPane pane) {
        TableView tableView = new TableView();
        List<TableColumn> tableColumn = new ArrayList<>();

        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox filterHbox = new HBox(20);
        HBox navigationHbox = new HBox(20);
        HBox addBookingHbox = new HBox(20);
        HBox removeBookingHbox = new HBox(20);

        pane.setCenter(centerVBox);

        //Create columns for our table
        for (int i = 0; i < COLUMN_STRINGS.length; i++) {
            tableColumn.add(new TableColumn(COLUMN_STRINGS[i]));
            tableColumn.get(i).setCellValueFactory(new PropertyValueFactory<>(OBSERVABLE_STRINGS[i]));
            tableView.getColumns().add(tableColumn.get(i));
        }

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        backBtn.setOnAction(e -> {
            BaseScene baseScene = new PrimaryScene("Primary");
            baseScene.setScene();
            hide();
        });

        /* Filtering */
        TextField clientFirstNameFilter = new TextField();
        clientFirstNameFilter.setPromptText("Filter By First Name");

        TextField clientLastNameFilter = new TextField();
        clientLastNameFilter.setPromptText("Filter By Last Name");

        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Filter By Booking ID");

        /* Adding Bookings */
        TextField trainerIdField = new TextField();
        trainerIdField.setPromptText("Trainer ID");

        TextField clientIdField = new TextField();
        clientIdField.setPromptText("Client ID");

        DateTimePicker dateTimeField = new DateTimePicker();
        dateTimeField.setPromptText("DATE & TIME");

        TextField durationField = new TextField();
        durationField.setPromptText("Duration");

        Button addBooking = new Button("Add Booking");

        TextField bookingIdRemoval = new TextField();
        bookingIdRemoval.setPromptText("Booking ID");

        Button removeBookingBtn = new Button("Remove Booking");


        /* Action Triggers */
        addBooking.setOnAction(e -> {
            try {
                output.write("add=" + trainerIdField.getText() + "=" + clientIdField.getText() + "=" + dateTimeField.getDateValue() + " " + dateTimeField.getTimeValue() + "=" + durationField.getText());
                output.newLine();
                output.flush();

                loadData(tableView);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        removeBookingBtn.setOnAction(e -> {
            try {
                output.write("remove=" + bookingIdRemoval.getText());
                output.newLine();
                output.flush();

                loadData(tableView);

                if (bookingIdRemoval.getText() != null) {
                    bookingIdRemoval.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /* Adding our filtering components to a list */
        textFieldFilters.add(clientFirstNameFilter);
        textFieldFilters.add(clientLastNameFilter);
        textFieldFilters.add(bookingIdField);

        navigationHbox.getChildren().add(backBtn);

        filterHbox.getChildren().addAll(clientFirstNameFilter, clientLastNameFilter, bookingIdField);
        filterHbox.getChildren().forEach(e -> {
            e.setStyle("-fx-alignment: CENTER");
        });

        addBookingHbox.getChildren().addAll(trainerIdField, clientIdField, dateTimeField, durationField, addBooking);
        addBookingHbox.getChildren().forEach(e -> {
            e.setStyle("-fx-alignment: CENTER");
        });

        removeBookingHbox.getChildren().addAll(bookingIdRemoval, removeBookingBtn);
        removeBookingHbox.getChildren().forEach(e -> {
            e.setStyle("-fx-alignment: CENTER");
        });

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, tableView, filterHbox, addBookingHbox, removeBookingHbox);

        try {
            output = new BufferedWriter(new OutputStreamWriter(Main.socket.getOutputStream()));
            loadData(tableView);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void filterTable(TableView tableView) {
        FilteredList<Booking> filteredData = new FilteredList<>(getTableData(), b -> true);

        textFieldFilters.forEach(l -> {
            l.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(booking -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (String.valueOf(booking.getBookingId()).equalsIgnoreCase(lowerCaseFilter)) {
                        return true;
                    }

                    if (booking.getClientFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }

                    if (booking.getClientLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }

                    return false;
                });
            });
        });

        SortedList<Booking> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }


    private void loadData(TableView tv) throws IOException {
        output.write("listall");
        output.newLine();
        output.flush();
        getBookings();
        tv.setItems(getTableData());
        filterTable(tv);
    }

    private Map<Integer, ArrayList<String>> bookings = new HashMap<>();

    private Map<Integer, ArrayList<String>> getBookings() {
        try {

            if (objectInput == null) {
                objectInput = new ObjectInputStream(Main.socket.getInputStream());
            }
            Object dataObject = objectInput.readObject();

            bookings = (Map<Integer, ArrayList<String>>) dataObject;

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

        ObservableList<Booking> collections = FXCollections.observableArrayList(bookings.entrySet().stream().map(
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

        return collections;
    }
}
