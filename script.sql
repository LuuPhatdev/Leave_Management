use
master
drop
database if exists LeaveManagement
create
database LeaveManagement
go
use LeaveManagement


create table Role
(
    role_id          int IDENTITY(1,1) primary key,
    role_title       varchar(25)  not null,
    role_description varchar(100) not null
)

create table Employee
(
    employee_id   int IDENTITY(1,1) primary key,
    dep_id        int                not null,
    fullname      varchar(25)        not null,
    gender        bit                not null,
    date_of_birth date               not null,
    phone         varchar(15) unique not null,
    email         varchar(50) unique not null,
    date_start    date               not null,
    annual_leave  int                not null,
    manager_id    int
)

create table Time_Off
(
    time_off_id          int IDENTITY(1,1) primary key,
    employee_id          int FOREIGN KEY REFERENCES Employee(employee_id) not null,
    date_time_off        date        not null,
    description_time_off varchar(50) not null,
    used                 int,
    accrued              double PRECISION,
    balance              double PRECISION
)

create table Account
(
    employee_id int primary key FOREIGN KEY REFERENCES Employee(employee_id) not null,
    role_id     int FOREIGN KEY REFERENCES Role (role_id) not null,
    username    varchar(25) unique not null,
    pass        varchar(25)        not null
)

create table Department
(
    dep_id    int IDENTITY(1,1) primary key,
    dep_title varchar(25) not null,
    chief_id  int unique  not null
)
create table Leave_Type
(
    leave_id   int IDENTITY(1,1) primary key,
    leave_type varchar(25) not null
)

create table Request
(
    request_id     int IDENTITY(1,1) primary key,
    employee_id    int FOREIGN KEY REFERENCES Employee(employee_id) not null,
    leave_id       int FOREIGN KEY REFERENCES Leave_Type(leave_id) not null,
    date_start     date        not null,
    date_end       date        not null,
    request_status varchar(10) not null,
    request_to     varchar(50) not null,
    amount         int         not null
)
    go

--     Insert values --
insert into role(role_title, role_description) values ('Admin', 'who can create accounts')
insert into role(role_title, role_description) values ('Employee', 'Employee')
insert into role(role_title, role_description) values ('Manager', 'who manages over their Employee')

insert into department(dep_title, chief_id) values('dep A', 1)
insert into department(dep_title, chief_id) values('dep B', 2)

insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(1, 'Yan Lee', 'TRUE', '1997-05-09', '0776983835', 'leeyanxmen@gmail.com', '2021-05-09', 12, NULL)
insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(1, 'Phat Luu', 'TRUE', '1997-11-15', '0123456789', 'LuuPhat@gmail.com', '2021-11-15', 7, NULL)
insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(1, 'Nam Nhat', 'TRUE', '1997-07-22', '9876543210', 'NhatNam@gmail.com', '2021-07-22', 10, 2)

insert into account(employee_id, role_id, username, pass) values (1, 1, 'leemongyan', '12345')
insert into account(employee_id, role_id, username, pass) values (2, 2, 'dingluuphat', '12345')
insert into account(employee_id, role_id, username, pass) values (3, 3, 'nguyennhatnam', '12345')

insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (1, '2022-05-09', 'nghi gi do', 0, 0, 0)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (1, '2022-05-10', 'nghi gi do', 0, 0, 0)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (2, '2022-05-11', 'nghi gi do', 0, 0, 0)

insert into leave_type(leave_type) values('Sick leave')
insert into leave_type(leave_type) values('Casual leave')
insert into leave_type(leave_type) values('Public holiday')
insert into leave_type(leave_type) values('Religious holidays')
insert into leave_type(leave_type) values('Maternity leave')
insert into leave_type(leave_type) values('Paternity leave')
insert into leave_type(leave_type) values('Bereavement leave')
insert into leave_type(leave_type) values('Compensatory leave')
insert into leave_type(leave_type) values('Sabbatical leave')
insert into leave_type(leave_type) values('Unpaid Leave')

insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, amount)
values (1, 1, '2023-05-09', '2023-06-09', 'pending', 'leeyanxmen@gmail.com', 11)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, amount)
values (1, 1, '2023-05-09', '2023-06-09', 'accepted', 'leeyanxmen@gmail.com', 11)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, amount)
values (2, 3, '2023-05-09', '2023-06-09', 'accepted', 'leeyanxmen@gmail.com', 11)
go