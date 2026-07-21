import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    public static String generateCity(Faker faker) {
        String[] cities = {"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Грозный", "Казань", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Кызыл", "Карачаевск", "Абакан", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Калининград", "Кострома", "Краснодар", "Красноярск", "Пермь", "Псков", "Ростов-на-Дону", "Самара", "Санкт-Петербург", "Саранск", "Саратов", "Владимир", "Великий Новгород", "Белгород", "Брянск", "Волгоград", "Вологда", "Иваново", "Ижевск", "Иркутск", "Калуга", "Кемерово", "Киров", "Курган", "Курск", "Липецк", "Магадан", "Мурманск", "Нижний Новгород", "Новосибирск", "Омск", "Оренбург", "Пенза", "Псков", "Рязань", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Хабаровск", "Ханты-Мансийск", "Чебоксары", "Челябинск", "Южно-Сахалинск", "Ярославль", "Москва", "Севастополь" };
        Random random = new Random();
        return cities[random.nextInt(cities.length)];
    }

    public static String generateName(Faker faker) {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generatePhone(Faker faker) {
        return String.format(
                "+7 (%03d) %03d-%02d-%02d",
                faker.number().numberBetween(900, 1000),
                faker.number().numberBetween(100, 1000),
                faker.number().numberBetween(10, 100),
                faker.number().numberBetween(10, 100)
        );
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            String city = generateCity(faker);
            String name = generateName(faker);
            String phone = generatePhone(faker);
            return new UserInfo(city, name, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
