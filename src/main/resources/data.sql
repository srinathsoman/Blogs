insert into post (id, title, content, user_id, created_at, updated_at) values
    (1, 'Welcome', 'Welcome to this Blog',100,current_timestamp,current_timestamp),
    (2, 'Test Post', 'This is a test post',100,current_timestamp,current_timestamp),
    (3, 'Hello', 'Wishing you a good day',100,current_timestamp,current_timestamp);
insert into comment (id, post_id, comment, user_id, created_at, updated_at) values
    (4, 1, 'Kilroy was here',100,current_timestamp,current_timestamp),
    (5, 1, 'Foobar too',100,current_timestamp,current_timestamp);
