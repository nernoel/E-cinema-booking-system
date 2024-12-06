/*
 * User repository to interact with the database
 * Extends JPA imported repository
 */

 package ecinema.booking.system.repository;
 import ecinema.booking.system.entity.User;
 
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
 
 import java.util.List;
 import java.util.Optional;
 
 @Repository
 public interface UserRepository extends JpaRepository<User, Long> {
     // Find user given email
     User findByEmail(String email);
 
     // Find user by their id
     Optional<User> findById(Long userId);
 
     // Get users who are subscribed to the promo status
     List<User> findByPromoStatus(User.PromoStatus PromoStatus);
 }