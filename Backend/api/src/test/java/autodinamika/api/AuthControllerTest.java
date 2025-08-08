import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import autodinamika.api.controller.AuthController;
import autodinamika.api.model.Seller;
import autodinamika.api.model.Role;
import autodinamika.api.repository.RoleRepository;
import autodinamika.api.repository.SellerRepository;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private RoleRepository roleRepository;

    private Map<String, String> validRegisterBody;

    @BeforeEach
    void setup() {
        validRegisterBody = Map.of(
            "email", "nuevo@email.com",
            "first_Name", "Luis",
            "last_Name", "Mendoza",
            "phone", "123456789",
            "password", "pass123",
            "address", "Calle Falsa 123",
            "birthday_Date", "1990-01-01"
        );
    }

    @Test
    public void testRegisterSuccess() {
        // Simular que email no existe
        when(sellerRepository.findByEmail("nuevo@email.com")).thenReturn(Optional.empty());

        // Simular recuperación de rol "seller"
        Role role = new Role();
        role.setRoleName("seller");
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        Map<String, Object> response = authController.register(validRegisterBody);

        assertTrue((Boolean) response.get("success"));
        assertEquals("Registered successfully", response.get("message"));
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }

    @Test
    public void testRegisterEmailAlreadyExists() {
        when(sellerRepository.findByEmail("nuevo@email.com")).thenReturn(Optional.of(new Seller()));

        Map<String, Object> response = authController.register(validRegisterBody);

        assertFalse((Boolean) response.get("success"));
        assertEquals("Email already registered", response.get("message"));
        verify(sellerRepository, never()).save(any(Seller.class));
    }


    @Test
    public void testLoginSuccess() {
        Seller seller = new Seller();
        seller.setSeller_ID(1L);
        seller.setFirst_Name("Luis");
        seller.setLast_Name("Mendoza");
        seller.setPassword("pass123");
        Role role = new Role();
        role.setRoleName("seller");
        seller.setRole(role);

        when(sellerRepository.findByEmail("user@email.com")).thenReturn(Optional.of(seller));

        Map<String,String> body = Map.of("email", "user@email.com", "password", "pass123");

        Map<String, Object> response = authController.login(body);

        assertTrue((Boolean) response.get("success"));
        assertEquals("Luis Mendoza", response.get("name"));
        assertEquals("seller", response.get("role"));
    }

    @Test
    public void testLoginFailInvalidCredentials(){
        when(sellerRepository.findByEmail("wrong@email.com")).thenReturn(Optional.empty());

        Map<String,String> body = Map.of("email", "wrong@email.com", "password", "any");

        Map<String, Object> response = authController.login(body);

        assertFalse((Boolean)response.get("success"));
        assertEquals("Invalid credentials", response.get("message"));
    }
}
