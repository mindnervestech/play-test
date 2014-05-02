# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table questions (
  id                        bigint auto_increment not null,
  question                  varchar(255),
  option1                   varchar(255),
  option2                   varchar(255),
  option3                   varchar(255),
  option4                   varchar(255),
  correct_answer            varchar(255),
  question_type             varchar(255),
  marks                     double
  constraint pk_questions primary key (id))
;

create table report (
  id                        bigint auto_increment not null,
  users_id                  bigint,
  question_id               bigint,
  answer                    varchar(255),
  remark                    varchar(255),
  marks                     double,
  constraint pk_report primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  marks                     double,
  code                      varchar(255),
  constraint uq_user_email unique (email),
  constraint uq_user_code unique (code),
  constraint pk_user primary key (id))
;

alter table report add constraint fk_report_users_1 foreign key (users_id) references user (id) on delete restrict on update restrict;
create index ix_report_users_1 on report (users_id);
alter table report add constraint fk_report_question_2 foreign key (question_id) references questions (id) on delete restrict on update restrict;
create index ix_report_question_2 on report (question_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table questions;

drop table report;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

