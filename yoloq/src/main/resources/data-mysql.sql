-- users (base password is 123123)
insert into user (username, password, email, first_name, last_name, role, is_deleted, user_type) values ('rixenzi', '$2a$10$0yQIi.4hevoFtXj61ANzfe4tKrn6I96D/NUe3soyu96B3GWYPT24q', 'rilakmateja@gmail.com', 'mateja', 'rilak', 0, false, 'ADMIN');
insert into user (username, password, email, first_name, last_name, role, is_deleted, user_type) values ('guest', '$2a$10$0yQIi.4hevoFtXj61ANzfe4tKrn6I96D/NUe3soyu96B3GWYPT24q', 'guest@gmail.com', 'guest', 'guest', 1, false, 'USER');
-- posts
insert into post(content, creation_date, is_deleted, user_id) values ('Test Content', '2023-05-21 11:52:08.156285', 0, 2);
--
insert into `group`(creation_date, description, is_deleted, is_suspended,name) values('2023-05-21 22:59:01.285302', 'Test', false, false, 'Test')