create table post (
    id bigint not null primary key,
    title varchar(128),
    content varchar(128),
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

CREATE SEQUENCE ID_SEQ
  START WITH 1
  INCREMENT BY 1;
