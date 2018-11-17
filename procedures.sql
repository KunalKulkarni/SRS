CREATE OR REPLACE PACKAGE procedures AS
	PROCEDURE show_students(recordsets out sys_refcursor);
	PROCEDURE show_Classes(recordsets out sys_refcursor);
	PROCEDURE show_TAs(recordsets out sys_refcursor);
	PROCEDURE show_Courses(recordsets out sys_refcursor);
	PROCEDURE show_Enrollement(recordsets out sys_refcursor);
	PROCEDURE show_Prerequisite(recordsets out sys_refcursor);
	PROCEDURE show_Logs(recordsets out sys_refcursor);
	PROCEDURE show_ClassTA(class_id in classes.classid%type,errorMsg out varchar2,recordsets out sys_refcursor);
	PROCEDURE CheckPrerequisites(deptcode in Prerequisites.dept_code%type,
		course_in in Prerequisites.course# %type,
		recordsets out sys_refcursor);
	END;
	/