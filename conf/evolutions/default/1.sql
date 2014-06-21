# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table logininfo (
  id                        bigint not null,
  email                     varchar(255),
  passwd                    varchar(255),
  constraint uq_logininfo_email unique (email),
  constraint pk_logininfo primary key (id))
;

create sequence logininfo_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists logininfo;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists logininfo_seq;

