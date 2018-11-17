CREATE OR REPLACE PACKAGE BODY procedures AS
	PROCEDURE show_students(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Students */
		OPEN recordsets FOR
			SELECT *
			FROM students;
	END;
	PROCEDURE show_Classes(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Classes */
		OPEN recordsets FOR
			SELECT *
			FROM Classes;
	END;
	PROCEDURE show_TAs(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All TAs*/
		OPEN recordsets FOR
			SELECT *
			FROM TAs;
	END;
	PROCEDURE show_Courses(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Courses */
		OPEN recordsets FOR
			SELECT *
			FROM Courses;
	END;
	PROCEDURE show_Enrollement(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Enrollements */
		OPEN recordsets FOR
			SELECT *
			FROM Enrollements;
	END;
	PROCEDURE show_Prerequisite(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Prerequisites */
		OPEN recordsets FOR
			SELECT *
			FROM Prerequisites;
	END;
	PROCEDURE show_Logs(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Logs */
		OPEN recordsets FOR
			SELECT *
			FROM Logs;
	END;
	
	PROCEDURE show_ClassTA(class_id in classes.classid%type,errorMsg out varchar2,recordsets out sys_refcursor) IS
	class_count number;
	TA_count number;
	BEGIN
	SELECT COUNT(*) INTO class_count FROM classes WHERE classid=class_id;
	IF class_count!=0
		THEN
			SELECT COUNT(*) INTO TA_count FROM classes WHERE classid=class_id AND TA_B# IS NOT NULL;
			IF TA_count!=0
			THEN
				OPEN recordsets FOR
				SELECT S.B#,S.first_name,S.last_name FROM STUDENTS S,CLASSES C,TAS T WHERE T.B#=C.TA_B# AND C.CLASSID=CLASS_ID AND S.B#=T.B#;
			ELSE
				errorMsg := 'Class Has No TA';
			END IF;
	ELSE
		errorMsg := 'Class ID Invalid';
	END IF;
	END;
	
	PROCEDURE CheckPrerequisites(deptcode in Prerequisites.dept_code%type,
		course_in in Prerequisites.course# %type,
		recordsets out sys_refcursor) IS
			BEGIN
				OPEN recordsets FOR
					SELECT (pre_dept_code || pre_course#) as COURSE_ID
					FROM (SELECT * FROM prerequisites
					START WITH dept_code=deptcode and course#=course_in
					CONNECT BY PRIOR pre_dept_code = dept_code and PRIOR pre_course# = course#);
			END;

END procedures;
	/
		show errors