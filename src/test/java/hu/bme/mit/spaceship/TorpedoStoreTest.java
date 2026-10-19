package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }
    @Test
    void fire_Success_DecreasesTorpedoCount() {
        // Arrange: a hibaráta 0, tehát a tüzelés mindig sikeres
        TorpedoStore store = new TorpedoStore(5, 0.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertTrue(result);
        assertEquals(4, store.getTorpedoCount());
    }

    @Test
    void fire_MultipleTorpedoes_Success() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        boolean result = store.fire(3);

        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedoes_Success() {
        // határeset: pontosan annyi torpedót lövünk ki, amennyi van
        TorpedoStore store = new TorpedoStore(2, 0.0);

        boolean result = store.fire(2);

        assertTrue(result);
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_Failure_DoesNotChangeTorpedoCount() {
        // Arrange: a hibaráta 1.0, tehát a tüzelés mindig sikertelen
        TorpedoStore store = new TorpedoStore(5, 1.0);

        boolean result = store.fire(1);

        assertFalse(result);
        assertEquals(5, store.getTorpedoCount());
    }

    @Test
    void fire_ZeroTorpedoes_ThrowsException() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_NegativeTorpedoes_ThrowsException() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
    }

    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        TorpedoStore store = new TorpedoStore(2, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(3));
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void fire_FromEmptyStore_ThrowsException() {
        TorpedoStore store = new TorpedoStore(0, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
    }

    @Test
    void isEmpty_NewStoreWithTorpedoes_ReturnsFalse() {
        TorpedoStore store = new TorpedoStore(3, 0.0);

        assertFalse(store.isEmpty());
    }

    @Test
    void isEmpty_NewStoreWithZeroTorpedoes_ReturnsTrue() {
        TorpedoStore store = new TorpedoStore(0, 0.0);

        assertTrue(store.isEmpty());
    }

    @Test
    void isEmpty_AfterFiringLastTorpedo_ReturnsTrue() {
        TorpedoStore store = new TorpedoStore(1, 0.0);

        store.fire(1);

        assertTrue(store.isEmpty());
    }

    @Test
    void getTorpedoCount_ReturnsInitialCount() {
        TorpedoStore store = new TorpedoStore(7, 0.0);

        assertEquals(7, store.getTorpedoCount());
    }
}

