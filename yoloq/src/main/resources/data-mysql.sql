-- users
insert into user (username, password, email, first_name, last_name, role, is_deleted) values ('rixenzi', '$2a$10$0yQIi.4hevoFtXj61ANzfe4tKrn6I96D/NUe3soyu96B3GWYPT24q', 'rilakmateja@gmail.com', 'mateja', 'rilak', 1, false);
-- posts
insert into post(content, creation_date, is_deleted, user_id) values ('Test Content', '2023-05-21 11:52:08.156285', 0, 1);
