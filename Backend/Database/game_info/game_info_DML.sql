-- This file is to populate the game_info database for testing
-- It is meant to look like we have played one game together

-- Duplicate error means you need to clear the tables

use game_info;

INSERT INTO `users` VALUES 
	(NULL, 'Mitchell', 'WD4Lyfe', 1),
	(NULL, 'Jay', 'beards&bikes', 1),
	(NULL, 'Ben', 'expueraments', 1),
    (NULL, 'Joseph', '2Tall?', 1),
    (NULL, 'Sumanth', 'TA0fFutu<e', 1);
    
INSERT INTO `user_stats` VALUES
	(1, 3, NULL, NULL, 1, 0, 1),
	(2, 2, NULL, NULL, 0,  1, 1),
	(3, 5, NULL, NULL, 0, 1, 1),
    (4, 0, NULL, NULL, 0, 1, 1),
    (5, 1, NULL, NULL, 0, 0, 1);
    
INSERT INTO `power_ups` VALUES
	(0, 'Silent Step', 'hide', 'causes the hider to move silently', 30),
    (1, 'Enhanced Hearing', 'seek', 'allows the seeker to "hear" better', 30),
    (2, 'Bigger Map', 'hide', 'Makes the seeker\'s map size bigger than usual', 30);
    
INSERT INTO `servers` VALUES
	(0, 0, 1, 0, 1, 0),
    (1, NULL, NULL, NULL, NULL, NULL);
