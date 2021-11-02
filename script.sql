use master
drop database if exists leave_management
create database leave_management
go

use Leave_Management
create table role(
                     role_id int IDENTITY(1,1) primary key,
                     role_title varchar(25) not null,
                     role_description varchar(100) not null
)

create table employee(
                         employee_id int IDENTITY(1,1) primary key,
                         dep_id int not null,
                         fullname varchar(25) not null,
                         gender tinyint not null,
                         date_of_birth date not null,
                         phone varchar(15) unique not null,
                         email varchar(50) unique not null,
                         date_start date not null,
                         annual_leave double PRECISION not null,
                         manager_id int null
)

create table time_off(
                         time_off_id int IDENTITY(1,1) primary key,
                         employee_id int FOREIGN KEY REFERENCES employee(employee_id) not null,
                         date_time_off date not null,
                         description_time_off varchar(50) not null,
                         used int,
                         accrued double PRECISION,
                         balance double PRECISION
)

create table account(
                        employee_id int primary key FOREIGN KEY REFERENCES employee(employee_id) not null,
                        role_id int FOREIGN KEY REFERENCES role(role_id) not null,
                        username varchar(25) unique not null,
                        pass varchar(25) not null
)

create table department(
                           dep_id int IDENTITY(1,1) primary key,
                           dep_title varchar(25) not null,
                           chief_id int unique not null
)

create table leave_type(
                           leave_id int IDENTITY(1,1) primary key,
                           leave_type varchar(25) not null
)

create table request(
                        request_id int IDENTITY(1,1) primary key,
                        employee_id int FOREIGN KEY REFERENCES employee(employee_id) not null,
                        leave_id int FOREIGN KEY REFERENCES leave_type(leave_id) not null,
                        date_start date not null,
                        date_end date not null,
                        request_status varchar(10) not null,
                        request_to varchar(50) not null,
                        request_description varchar(200) not null,
                        amount int not null
)

go

insert into role(role_title, role_description) values ('Admin', 'who can create accounts')
insert into role(role_title, role_description) values ('Employee', 'Employee')
insert into role(role_title, role_description) values ('Manager', 'who manages over their Employee')

insert into department(dep_title, chief_id) values('department resolve', 2)
insert into department(dep_title, chief_id) values('dep B', 4)

insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(1, 'Yan Lee', 1, '1997-05-09', '0776983835', 'admin123@gmail.com', '2021-05-09', 6.32, NULL)
insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(1, 'Duong Ly', 1, '1997-11-15', '0123456987', 'leeyanxmen@gmail.com', '2021-11-16', 7.48, NULL)
insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(2, 'Phat Luu', 1, '1997-11-15', '0123456789', 'LuuPhat@gmail.com', '2021-11-15', 6.68, 4)
insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
values(2, 'Nam Nhat', 1, '1997-07-22', '9876543210', 'NhatNam@gmail.com', '2021-07-22', 4.44, NULL)

insert into account(employee_id, role_id, username, pass) values (1, 1, 'leemongyan', '12345')
insert into account(employee_id, role_id, username, pass) values (2, 3, 'lymanhduong', '12345')
insert into account(employee_id, role_id, username, pass) values (3, 2, 'dingluuphat', '12345')
insert into account(employee_id, role_id, username, pass) values (4, 3, 'nguyennhatnam', '12345')

insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (2, '2021-09-09', 'nghi gi do', 1, 0, 5.14)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (2, '2021-10-01', 'annual leave', 0, 1.17, 6.31)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (2, '2021-11-01', 'annual leave', 0, 1.17, 7.48)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (3, '2021-09-09', 'nghi gi do', 1, 0, 4.34)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (3, '2021-10-01', 'annual leave', 0, 1.17, 5.51)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (3, '2021-11-01', 'annual leave', 0, 1.17, 6.68)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (4, '2021-09-09', 'nghi gi do', 1, 0, 3.44)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (4, '2021-10-01', 'annual leave', 0, 1.17, 5.61)
insert into time_off(employee_id, date_time_off, description_time_off, used, accrued, balance)
values (4, '2021-11-01', 'annual leave', 0, 1.17, 6.78)

insert into leave_type(leave_type) values('Sick leave')
insert into leave_type(leave_type) values('Annual leave')
insert into leave_type(leave_type) values('Public holiday')
insert into leave_type(leave_type) values('Religious holidays')
insert into leave_type(leave_type) values('Maternity leave')
insert into leave_type(leave_type) values('Paternity leave')
insert into leave_type(leave_type) values('Bereavement leave')
insert into leave_type(leave_type) values('Compensatory leave')
insert into leave_type(leave_type) values('Sabbatical leave')
insert into leave_type(leave_type) values('Unpaid Leave')

insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (2, 1, '2021-05-09', '2021-05-10', 'accepted', 'leeyanxmen@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (2, 1, '2021-06-13', '2021-06-14', 'denied', 'leeyanxmen@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (2, 1, '2021-11-10', '2021-11-15', 'pending', 'leeyanxmen@gmail.com', '...', 5)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (3, 1, '2021-05-09', '2021-05-10', 'accepted', 'NhatNam@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (3, 1, '2021-06-13', '2021-06-14', 'denied', 'NhatNam@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (3, 1, '2021-11-10', '2021-11-15', 'pending', 'NhatNam@gmail.com', '...', 5)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (4, 1, '2021-05-09', '2021-05-10', 'accepted', 'leeyanxmen@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (4, 1, '2021-06-13', '2021-06-14', 'denied', 'leeyanxmen@gmail.com', '...', 2)
insert into request(employee_id, leave_id, date_start, date_end, request_status, request_to, request_description, amount)
values (4, 1, '2021-11-10', '2021-11-15', 'pending', 'leeyanxmen@gmail.com', '...', 5)
go

-- Insert employee --
create proc insertEmployee @dep_id int, @fullname varchar(25), @gender bit, @date_of_birth date, @phone varchar(15),
                           @email varchar(50), @date_start date, @annual_leave double precision, @manager_id int
as
begin
    insert into employee(dep_id, fullname, gender, date_of_birth, phone, email, date_start, annual_leave, manager_id)
    values (@dep_id, @fullname, @gender, @date_of_birth, @phone, @email, @date_start, @annual_leave, @manager_id)
end
go

-- Insert Account ---
create proc insertAccount @employee_id int, @role_id int, @username varchar(25), @pass varchar(25)
as
begin
    insert into account(employee_id, role_id, username, pass)
    values (@employee_id, @role_id, @username, @pass)
end
go

-- Select all account --
create proc seAllAccount
as
begin
    select * from account
end
go

-- Update Account --
create proc updateAccount @employee_id int, @username varchar(25), @pass varchar(25)
as
begin
    update account
    set username=@username,
        pass=@pass
    where employee_id = @employee_id
end

-- Update Annual leave --
update [employee]
set annual_leave = annual_leave + 1

    USE [msdb]
GO

-- Update +1 date
/****** Object:  Job [update_automatically]    Script Date: 10/30/2021 4:45:58 AM ******/
BEGIN TRANSACTION
DECLARE @ReturnCode INT
SELECT @ReturnCode = 0
/****** Object:  JobCategory [[Uncategorized (Local)]]    Script Date: 10/30/2021 4:45:58 AM ******/
IF NOT EXISTS (SELECT name FROM msdb.dbo.syscategories WHERE name=N'[Uncategorized (Local)]' AND category_class=1)
    BEGIN
        EXEC @ReturnCode = msdb.dbo.sp_add_category @class=N'JOB', @type=N'LOCAL', @name=N'[Uncategorized (Local)]'
        IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback

    END

DECLARE @jobId BINARY(16)
EXEC @ReturnCode =  msdb.dbo.sp_add_job @job_name=N'update_automatically',
                    @enabled=1,
                    @notify_level_eventlog=0,
                    @notify_level_email=0,
                    @notify_level_netsend=0,
                    @notify_level_page=0,
                    @delete_level=0,
                    @description=N'No description available.',
                    @category_name=N'[Uncategorized (Local)]',
                    @owner_login_name=N'sa', @job_id = @jobId OUTPUT
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
/****** Object:  Step [Step update]    Script Date: 10/30/2021 4:45:58 AM ******/
EXEC @ReturnCode = msdb.dbo.sp_add_jobstep @job_id=@jobId, @step_name=N'Step update',
                   @step_id=1,
                   @cmdexec_success_code=0,
                   @on_success_action=1,
                   @on_success_step_id=0,
                   @on_fail_action=2,
                   @on_fail_step_id=0,
                   @retry_attempts=0,
                   @retry_interval=0,
                   @os_run_priority=0, @subsystem=N'TSQL',
                   @command=N'use leave_management
   update [employee]
        set annual_leave = annual_leave + 1',
                   @database_name=N'leave_management',
                   @flags=0
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_update_job @job_id = @jobId, @start_step_id = 1
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobschedule @job_id=@jobId, @name=N'update_annualLeave',
                   @enabled=1,
                   @freq_type=32,
                   @freq_interval=8,
                   @freq_subday_type=1,
                   @freq_subday_interval=0,
                   @freq_relative_interval=1,
                   @freq_recurrence_factor=1,
                   @active_start_date=20211029,
                   @active_end_date=99991231,
                   @active_start_time=0,
                   @active_end_time=235959,
                   @schedule_uid=N'9bdce4a4-df98-4858-909d-9bb9713cc4fc'
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobserver @job_id = @jobId, @server_name = N'(local)'
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
COMMIT TRANSACTION
GOTO EndSave
QuitWithRollback:
IF (@@TRANCOUNT > 0) ROLLBACK TRANSACTION
EndSave:
GO


-- Refresh after june
USE [msdb]
GO

/****** Object:  Job [reset_annual_leave]    Script Date: 10/30/2021 4:49:21 AM ******/
BEGIN TRANSACTION
DECLARE @ReturnCode INT
SELECT @ReturnCode = 0
/****** Object:  JobCategory [[Uncategorized (Local)]]    Script Date: 10/30/2021 4:49:21 AM ******/
IF NOT EXISTS (SELECT name FROM msdb.dbo.syscategories WHERE name=N'[Uncategorized (Local)]' AND category_class=1)
    BEGIN
        EXEC @ReturnCode = msdb.dbo.sp_add_category @class=N'JOB', @type=N'LOCAL', @name=N'[Uncategorized (Local)]'
        IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback

    END

DECLARE @jobId BINARY(16)
EXEC @ReturnCode =  msdb.dbo.sp_add_job @job_name=N'reset_annual_leave',
                    @enabled=1,
                    @notify_level_eventlog=0,
                    @notify_level_email=0,
                    @notify_level_netsend=0,
                    @notify_level_page=0,
                    @delete_level=0,
                    @description=N'No description available.',
                    @category_name=N'[Uncategorized (Local)]',
                    @owner_login_name=N'sa', @job_id = @jobId OUTPUT
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
/****** Object:  Step [reset_annual_leave]    Script Date: 10/30/2021 4:49:22 AM ******/
EXEC @ReturnCode = msdb.dbo.sp_add_jobstep @job_id=@jobId, @step_name=N'reset_annual_leave',
                   @step_id=1,
                   @cmdexec_success_code=0,
                   @on_success_action=1,
                   @on_success_step_id=0,
                   @on_fail_action=2,
                   @on_fail_step_id=0,
                   @retry_attempts=0,
                   @retry_interval=0,
                   @os_run_priority=0, @subsystem=N'TSQL',
                   @command=N'use leave_management
update [employee]
set annual_leave = 6
where annual_leave >=6
go',
                   @database_name=N'leave_management',
                   @flags=0
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_update_job @job_id = @jobId, @start_step_id = 1
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobschedule @job_id=@jobId, @name=N'reset_annual_leave',
                   @enabled=0,
                   @freq_type=1,
                   @freq_interval=0,
                   @freq_subday_type=0,
                   @freq_subday_interval=0,
                   @freq_relative_interval=0,
                   @freq_recurrence_factor=0,
                   @active_start_date=20220601,
                   @active_end_date=99991231,
                   @active_start_time=0,
                   @active_end_time=235959,
                   @schedule_uid=N'c75069e2-0a51-4891-aaaf-912240133589'
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
EXEC @ReturnCode = msdb.dbo.sp_add_jobserver @job_id = @jobId, @server_name = N'(local)'
IF (@@ERROR <> 0 OR @ReturnCode <> 0) GOTO QuitWithRollback
COMMIT TRANSACTION
GOTO EndSave
QuitWithRollback:
IF (@@TRANCOUNT > 0) ROLLBACK TRANSACTION
EndSave:
GO
