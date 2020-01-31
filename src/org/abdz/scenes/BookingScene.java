package org.abdz.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abdz.cst2550.sample.Main;
import org.abdz.utils.Booking;
import org.abdz.utils.SceneUtils;

import java.io.IOException;
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


    private List<TextField> textFieldFilters = new ArrayList<>();
    private SceneUtils sceneUtils = new SceneUtils();
    private Map<Integer, ArrayList<String>> bookings = new HashMap<>();

    @Override
    public void fillScene(BorderPane pane) {
        TableView tableView = new TableView();
//        List used to store our table data.
        List<TableColumn> tableColumn = new ArrayList<>();

        VBox centerVBox = sceneUtils.getCenterVBox(pane, 20, 0, 0);
        HBox filterHbox = new HBox(20);
        HBox navigationHbox = new HBox(20);
        HBox addBookingHbox = new HBox(20);
        HBox removeBookingHbox = new HBox(20);

        pane.setCenter(centerVBox);

//          Create columns for our table using a for loop where we iterate over our array
        for (int i = 0; i < COLUMN_STRINGS.length; i++) {
//            Add each new column to our tableColumn list
            tableColumn.add(new TableColumn(COLUMN_STRINGS[i]));
//            Set a value factory on our column
            tableColumn.get(i).setCellValueFactory(new PropertyValueFactory<>(OBSERVABLE_STRINGS[i]));
//            Finally add the new column to our tableView to display as a table for the user
            tableView.getColumns().add(tableColumn.get(i));
        }

        Button backBtn = new Button("< Back");
        backBtn.setPrefWidth(100);

        Label statusLabel = new Label();

//        A trigger action for the back button, which will return to previous page when clicked.
        backBtn.setOnAction(e -> {
//            A new scene is set
            BaseScene primaryScene = new PrimaryScene("Gym Bookings");
            primaryScene.setScene();
//            Hide current scene and reveal the new scene.
            hide();
        });

        /* Filtering Components */
        TextField clientFirstNameFilter = new TextField();
        clientFirstNameFilter.setPromptText("Filter By First Name");

        TextField clientLastNameFilter = new TextField();
        clientLastNameFilter.setPromptText("Filter By Last Name");

        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Filter By Booking ID");

        /* Remove Bookinge */
        TextField bookingIdRemoval = new TextField();
        bookingIdRemoval.setPromptText("Booking ID");

        Button removeBookingBtn = new Button("Remove Booking");
        removeBookingBtn.setPrefWidth(150);


        /* Action Triggers */
        removeBookingBtn.setOnAction(e -> {
            try {
//                Write to the stream to send a message to our server
                Main.outputStream.writeUTF("remove=" + bookingIdRemoval.getText());
//                Flush the stream so that the message is sent over immediately
                Main.outputStream.flush();
//                Load the data for our new table
                loadData(tableView);

//                Check if the booking id textfield is not null
                if (bookingIdRemoval.getText() != null) {
//                    Set the text for the label
                    statusLabel.setText("Successfully removed Booking with ID: " + bookingIdField.getText() + "!");
//                    Set red colour to our font
                    statusLabel.setStyle("-fx-mid-text-color: red;");
//                    Clear the textfield so it's empty
                    bookingIdRemoval.clear();
                }
            } catch (IOException ex) {
                System.err.println("Can't write to server.");
                ex.printStackTrace();
            }
        });
//      Make table headings keep the same size across our table
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        /* Adding our filtering components to a list */
        textFieldFilters.add(clientFirstNameFilter);
        textFieldFilters.add(clientLastNameFilter);
        textFieldFilters.add(bookingIdField);

        navigationHbox.getChildren().add(backBtn);

//        Use a for each loop to add CSS to our components
        filterHbox.getChildren().addAll(clientFirstNameFilter, clientLastNameFilter, bookingIdField);
        filterHbox.getChildren().forEach(e -> {
            e.setStyle("-fx-alignment: CENTER");
        });

//        Use a for each loop to add CSS to our components
        removeBookingHbox.getChildren().addAll(bookingIdRemoval, removeBookingBtn);
        removeBookingHbox.getChildren().forEach(e -> {
            e.setStyle("-fx-alignment: CENTER");
        });

        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.getChildren().addAll(navigationHbox, tableView, filterHbox, addBookingHbox, removeBookingHbox, statusLabel);

        loadData(tableView);
    }

    /**
     * A filter method to filter our table according to the inputs from the user.
     *
     * @param tableView table view object that has been populated
     */
    private void filterTable(TableView tableView) {
//        Make a filtered list using our table data from our getTableData() method
        FilteredList<Booking> filteredData = new FilteredList<>(getTableData(), b -> true);

//        For each loop on our textfield components
        textFieldFilters.forEach(l -> {
//            Add a listener to each textfield component to listen for any changes
            l.textProperty().addListener((observable, oldValue, newValue) -> {
//                Set a predicate for our filtered data using lambda as well
                filteredData.setPredicate(booking -> {
//                    If the new value is not there (null) or empty then return true
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

//                    Convert new value to lowercase for consistency
                    String lowerCaseFilter = newValue.toLowerCase();

//                    Compare our data to the new inputted field to find any criteria matching
                    if (String.valueOf(booking.getBookingId()).equalsIgnoreCase(lowerCaseFilter)) {
                        return true;
                    }

//                    Compare our data to the new inputted field to find any criteria matching
                    if (booking.getClientLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
//                    Compare our data to the new inputted field to find any criteria matching
                    if (booking.getClientFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }

//                    Otherwise return false since we did not find anything that matches our input
                    return false;
                });
            });
        });

//        Sort the list that needs to be displayed
        SortedList<Booking> sortedData = new SortedList<>(filteredData);

//        Bind our sorted data to our table view
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

//        Populate the newly sorted data to the table
        tableView.setItems(sortedData);
    }


    /**
     * Get the data from our server and store it in a list
     *
     * @param tv table view object that creates a table
     * @throws IOException we could not connect to the stream, therefore we'll catch the error
     */
    private void loadData(TableView tv) {
        try {
//            Write to the stream
            Main.outputStream.writeUTF("listall");
//            Flush the stream to send the message over immediately
            Main.outputStream.flush();
//            Get all the bookings that we have received from the server
            getBookings();
//            Populate the new data on the table
            tv.setItems(getTableData());
//            Add a filter to our table
            filterTable(tv);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the object from our server and returns it as a map for modification
     *
     * @return a list of the bookings
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
            System.out.println("Can't fetch bookings from server.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
