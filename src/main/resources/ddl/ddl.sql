-- ユーザテーブル
create table users (
  id UUID not null,
  user_cd varchar(10) not null,
  created_at timestamp not null,
  created_staff_id UUID not null,
  updated_at timestamp not null,
  updated_staff_id UUID not null,
  deleted_at timestamp,
  deleted_staff_id UUID,
  PRIMARY KEY (id),
  UNIQUE(user_cd)
);
comment on table users is 'ユーザ';
comment on column users.id is 'ID';
comment on column users.user_cd is 'ユーザコード';
comment on column users.created_at is '登録日時';
comment on column users.created_staff_id is '登録者スタッフID';
comment on column users.updated_at is '更新日時';
comment on column users.updated_staff_id is '更新者スタッフID';
comment on column users.deleted_at is '削除日時';
comment on column users.deleted_staff_id is '削除者スタッフID';

-- ユーザ詳細テーブル
create table user_details (
  id UUID not null,
  user_id UUID not null,
  user_name varchar(50) not null,
  user_name_kana varchar(100),
  mailaddress varchar(254) not null,
  zip_cd varchar(7) not null,
  pref_cd varchar(2) not null,
  address varchar(256) not null,
  tel varchar(13) not null,
  birthday varchar(10) not null,
  gender integer,
  note text,
  PRIMARY KEY (id),
  UNIQUE(user_id)
);
comment on table user_details is 'ユーザ詳細';
comment on column user_details.id is 'ID';
comment on column user_details.user_id is 'ユーザID';
comment on column user_details.user_name is 'ユーザ名';
comment on column user_details.user_name_kana is 'ユーザ名カナ';
comment on column user_details.mailaddress is 'メールアドレス';
comment on column user_details.zip_cd is '郵便番号';
comment on column user_details.pref_cd is '都道府県コード';
comment on column user_details.address is '住所';
comment on column user_details.tel is '連絡先';
comment on column user_details.birthday is '生年月日';
comment on column user_details.gender is '性別';
comment on column user_details.note is '備考';

-- ユーザ付加情報テーブル
create table user_additions (
  id UUID not null,
  user_id UUID not null,
  addition varchar(3) not null,
  PRIMARY KEY (id),
  UNIQUE(user_id, addition)
);
comment on table user_additions is 'ユーザ付加情報';
comment on column user_additions.id is 'ID';
comment on column user_additions.user_id is 'ユーザID';
comment on column user_additions.addition is '付加情報';

-- ユーザコードのシーケンス
create sequence user_cd_seq
  increment 1
  minvalue 1
;