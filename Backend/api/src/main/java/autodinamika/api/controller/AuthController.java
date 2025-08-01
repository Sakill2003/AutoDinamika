package autodinamika.api.controller;

import autodinamika.api.model.Role;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.RoleRepository;
import autodinamika.api.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        if (sellerRepository.findByEmail(email).isPresent()) {
            return Map.of("success", false, "message", "Email already registered");
        }

        Role role = roleRepository.findById(2L).orElseThrow(); // ID 2 = seller

        Seller seller = new Seller();
        seller.setFirst_Name(body.get("first_Name"));
        seller.setLast_Name(body.get("last_Name"));
        seller.setEmail(email);
        seller.setPhone(body.get("phone"));
        seller.setPassword(body.get("password"));
        seller.setAddress(body.get("address"));
        seller.setBirthday_Date(java.time.LocalDate.parse(body.get("birthday_Date")));
        seller.setRole(role);

        sellerRepository.save(seller);

        return Map.of("success", true, "message", "Registered successfully");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<Seller> sellerOpt = sellerRepository.findByEmail(email);

        if (sellerOpt.isEmpty() || !sellerOpt.get().getPassword().equals(password)) {
            return Map.of("success", false, "message", "Invalid credentials");
        }

        Seller seller = sellerOpt.get();

        return Map.of(
                "success", true,
                "seller_ID", seller.getSeller_ID(),
                "name", seller.getFirst_Name() + " " + seller.getLast_Name(),
                "role", seller.getRole().getRoleName()
        );
    }
}
