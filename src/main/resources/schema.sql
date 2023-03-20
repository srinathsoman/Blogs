create table post (
    id bigint not null primary key,
    title varchar(200),
    content text,
    user_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table comment (
    id bigint not null primary key,
    post_id bigint not null references post (id),
    comment varchar(160),
    user_id bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null
);

create table blocked_list(
    id bigint not null primary key,
    user_id bigint not null,
    blocked_user_id bigint not null
);

CREATE SEQUENCE POST_SEQ
  START WITH 10
  INCREMENT BY 1;

CREATE SEQUENCE COMMENT_SEQ
  START WITH 10
  INCREMENT BY 1;

CREATE SEQUENCE BLOCK_SEQ
  START WITH 10
  INCREMENT BY 1;

