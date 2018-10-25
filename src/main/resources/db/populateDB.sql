DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM user_meals;
DELETE FROM meals;


ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories) VALUES
    ('Завтрак', 500),
    ('Обед', 1000),
    ('Ужин', 700),
    ('Полдник', 300),
    ('Завтрак', 400);

INSERT INTO user_meals(user_id, meal_id) VALUES
      ( 100000, 100002),
     ( 100000, 100003),
     ( 100000, 100004),
     ( 100001, 100005),
    ( 100001,100006);

