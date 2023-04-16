-- users

insert into user (username, password, email, first_name, last_name, profile_image_path, is_deleted) values ('rixenzi', '123123', 'rilakmateja@gmail.com', 'mateja', 'rilak', '123123231', false);



-- posts
insert into post (content, creation_date, is_deleted, user_id) values ('test123', '2023-04-06', false, 1);