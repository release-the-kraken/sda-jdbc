package jdbc;

import jdbc.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOTest {
    private TaskDAO taskDAO = new TaskDAO();

    @Test
    void readShouldThrowIllegalArgumentExceptionFor0() throws SQLException {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.read(0));
    }
    @Test
    void readShouldThrowIllegalArgumentExceptionForNegativeNumber() throws SQLException {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.read(-6));
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionFor0() {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.delete(0));
    }
    @Test
    void deleteShouldThrowIllegalArgumentExceptionForNegativeNumber() {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.delete(-3));
    }

    @Test
    void readAllForUserShouldThrowIllegalArgumentExceptionForEmptyString() {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.readAllForUser(""));
    }
    @Test
    void readAllForUserShouldThrowIllegalArgumentExceptionForNull() {
        //then
        assertThrows(IllegalArgumentException.class, () -> taskDAO.readAllForUser(null));
    }
}