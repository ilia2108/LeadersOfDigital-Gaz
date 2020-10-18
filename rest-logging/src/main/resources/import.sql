INSERT INTO logging(id, userID, msg) 
    values (nextval('hibernate_sequence'), 1, 'Called client');