CREATE OR REPLACE PACKAGE BODY procedures AS
	PROCEDURE show_students(recordsets out sys_refcursor) IS
	BEGIN
    		/* Display All Students */
		OPEN recordsets FOR
			SELECT *
			FROM students;
	END;	
		END procedures;
		/
		