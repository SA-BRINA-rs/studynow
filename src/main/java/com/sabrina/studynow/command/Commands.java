package com.sabrina.studynow.command;

import com.sabrina.studynow.address.Address;
import com.sabrina.studynow.address.AddressRepository;
import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseRepository;
import com.sabrina.studynow.course.favorite.Favorite;
import com.sabrina.studynow.course.favorite.FavoriteRepository;
import com.sabrina.studynow.course.mode.Mode;
import com.sabrina.studynow.course.mode.ModeRepository;
import com.sabrina.studynow.course.rate.Rate;
import com.sabrina.studynow.course.rate.RateRepository;
import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.institution.InstitutionRepository;
import com.sabrina.studynow.institution.card.InstitutionCard;
import com.sabrina.studynow.token.APIToken;
import com.sabrina.studynow.token.APITokenNullObject;
import com.sabrina.studynow.token.APITokenRepository;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserRepository;
import com.sabrina.studynow.user.UserRole;
import com.sabrina.studynow.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class Commands {

    private final UserService userService;
    private final AddressRepository addressRepository;
    private final APITokenRepository apiTokenRepository;
    private final InstitutionRepository institutionRepository;
    private final ModeRepository modeRepository;
    private final CourseRepository courseRepository;
    private final FavoriteRepository favoriteRepository;
    private final RateRepository rateRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Commands(
            UserService userService,
            AddressRepository addressRepository,
            APITokenRepository apiTokenRepository,
            InstitutionRepository institutionRepository,
            ModeRepository modeRepository,
            CourseRepository courseRepository,
            FavoriteRepository favoriteRepository,
            RateRepository rateRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.addressRepository = addressRepository;
        this.apiTokenRepository = apiTokenRepository;
        this.institutionRepository = institutionRepository;
        this.modeRepository = modeRepository;
        this.courseRepository = courseRepository;
        this.favoriteRepository = favoriteRepository;
        this.rateRepository = rateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {

            User sabrina = User.builder()
                    .id(1L)
                    .firstName("Sabrina")
                    .lastName("Rodrigues Siqueira")
                    .email("sabrina.rodrigues@email.com")
                    .password("12345678")
                    .build();
            userService.add(sabrina);

            User nationalCollegeIreland = User.builder()
                    .id(2L)
                    .firstName("National College of Ireland")
                    .lastName("National College of Ireland")
                    .email("national.collegeireland@email.com")
                    .userRole(UserRole.INSTITUTION)
                    .password("12345678")
                    .build();
            userService.add(nationalCollegeIreland);

            Address address = Address.builder()
                    .user(nationalCollegeIreland)
                    .address1("23 Upper Pembroke Street")
                    .address2("House")
                    .city("Dublin")
                    .state("Dublin")
                    .zip("D02")
                    .country("Ireland")
                    .build();
            addressRepository.save(address);

            APIToken apiToken = APIToken.builder()
                    .user(nationalCollegeIreland)
                    .expirationDate(LocalDate.now())
                    .active(true)
                    .neverExpires(true)
                    .build();
            apiTokenRepository.save(apiToken);

            Institution institution = Institution.builder()
                    .id(1L)
                    .user(nationalCollegeIreland)
                    .name("National College of Ireland")
                    .phone("353 1 4498 500")
                    .description("National College of Ireland (NCI) is a third-level education college in Dublin. " +
                            "Founded in 1951, it offers full and part-time courses from certificate to degree and postgraduate level " +
                            "in areas related to commerce, industry, and management.")
                    .tags(Arrays.asList("College", "University", "Education"))
                    .build();
            institutionRepository.save(institution);

            Mode mode = Mode.builder()
                    .institution(institution)
                    .name("Online")
                    .description("Online courses")
                    .build();
            modeRepository.save(mode);

            Course course1 = Course.builder()
                    .id(1L)
                    .institution(institution)
                    .name("BSc (Honours) in Computing")
                    .price(15000.00)
                    .startDate(LocalDate.now().plusYears(2))
                    .endDate(LocalDate.now().plusYears(6))
                    .subject("Computing")
                    .description("The BSc (Honours) in Computing is a four-year, " +
                            "part-time programme of study that provides a comprehensive and " +
                            "up-to-date coverage of computing and IT topics. " +
                            "It is designed to equip students with the knowledge and skills " +
                            "to develop innovative solutions which the modern business environment demands.")
                    .mode(mode)
                    .tags(Arrays.asList("Computing", "IT", "Technology"))
                    .build();
            courseRepository.save(course1);

            Course course2 = Course.builder()
                    .id(2L)
                    .institution(institution)
                    .name("MSc in Cloud Computing")
                    .price(10000.00)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusYears(4))
                    .subject("Computing")
                    .description("The MSc in Cloud Computing is a taught postgraduate programme " +
                            "aiming to produce graduates with a good understanding of cloud computing " +
                            "and a solid foundation in the skills and techniques required to manage " +
                            "cloud computing platforms and services.")
                    .mode(mode)
                    .tags(Arrays.asList("Computing", "IT", "Technology"))
                    .build();
            courseRepository.save(course2);

            Favorite favorite1 = Favorite.builder()
                    .user(sabrina)
                    .course(course1)
                    .build();
            favoriteRepository.save(favorite1);

            Favorite favorite2 = Favorite.builder()
                    .user(sabrina)
                    .course(course2)
                    .build();
            favoriteRepository.save(favorite2);

            Rate rate1 = Rate.builder()
                    .user(sabrina)
                    .course(course1)
                    .rate(5)
                    .comment("Very good course and teachers are very helpful")
                    .build();
            rateRepository.save(rate1);

            Rate rate2 = Rate.builder()
                    .user(sabrina)
                    .course(course2)
                    .rate(3)
                    .comment("Good course and teachers are very helpful")
                    .build();
            rateRepository.save(rate2);

            Course courseTest = Course.builder()
                    .id(3L)
                    .institution(institution)
//                    .name("BSc (Honours) in Computing")
                    .price(-1.00)
//                    .startDate(LocalDate.now())
//                    .endDate(LocalDate.now().plusYears(4))
                    .mode(mode)
//                    .subject("Computing")
//                    .description("")
                    .tags(Arrays.asList("IT", "Technology"))
                    .build();

            Double maxPrice = -1d;
            System.out.println(courseRepository.searchByKeyword(courseTest, maxPrice));
            courseTest.setName("Cloud");
            System.out.println(courseRepository.searchByCourseCardsKeyword(courseTest, maxPrice));

        };
    }

}
