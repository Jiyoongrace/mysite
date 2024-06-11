desc user;
desc board;

-- join
insert into user values(null, '귤귤', 'guel@mysite.com', password('1234'), 'female', current_date(), 'ADMIN');

-- login
select no, name from user where email = 'giyun3632@naver.com' and password=password('1234');

-- test
select * from user;
select * from guestbook;
select * from board;
select * from emaillist;

-- role 추가
alter table user add column role enum('ADMIN', 'USER') not null default 'USER';

insert into board values(null, '포스코DX 8기 모집합니다~', '포스코DX의 미래를 그려나가실~\n 인재들을 모집합니다~~\n환영합니다~~\n', 0, now(), 1, 1, 0, 1);
insert into board values(null, '저 신청할래요', '참가신청 방법 알려주세요\n우와~~\n', 0, now(), 1, 2, 1, 3);
insert into board values(null, 'test', 'test', 0, now(), 1, 2, 1, 3);

select no, name, contents, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as regDate
        from guestbook
        order by reg_date desc;
        
delete from board;
delete from user where name = '귤귤';

update user set role = 'ADMIN' where no = 1;
update user set password = password('1234') where no = 1;