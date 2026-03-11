import java.util.*;

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class CancellationService {

    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        releasedRoomIds.push(reservationId);

        int currentAvailability = inventory.getRoomAvailability().get(roomType);
        inventory.updateAvailability(roomType, currentAvailability + 1);

        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        for (String id : releasedRoomIds) {
            System.out.println("Released Reservation ID: " + id);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        cancellationService.registerBooking("Single-1", "Single");

        cancellationService.cancelBooking("Single-1", inventory);

        cancellationService.showRollbackHistory();

        int updatedAvailability = inventory.getRoomAvailability().get("Single");

        System.out.println("\nUpdated Single Room Availability: " + updatedAvailability);
    }
}