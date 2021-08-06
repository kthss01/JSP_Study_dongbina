CREATE TABLE bbs (
    bbsID NUMBER PRIMARY KEY,
    bbsTitle VARCHAR(50),
    userID VARCHAR(20),
    bbsDate DATE,
    bbsContent VARCHAR(2048),
    bbsAvailable NUMBER
);

CREATE TABLE BBS_USER (
    userID VARCHAR(20) PRIMARY KEY,
    userPassword VARCHAR(20),
    userName VARCHAR(20),
    userGender VARCHAR(20),
    userEmail VARCHAR(50)
);

-- 현재 날짜
SELECT SYSDATE FROM DUAL;
--SELECT SYSDATE; -- 이건 안됨
SELECT SYSTIMESTAMP FROM DUAL;

-- bssID < ? 이고 bbsAvailable = 1 이며 bbsID 순 정렬 하고 10개만 보여주기
SELECT *
FROM ( SELECT * FROM BBS WHERE bbsID < 11 AND bbsAvailable = 1 ORDER BY bbsID )
WHERE ROWNUM <= 10;

SELECT * FROM ( SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC ) WHERE ROWNUM <= 10;

SELECT * FROM ( SELECT * FROM BBS WHERE bbsID < 15 AND bbsAvailable = 1 ORDER BY bbsID ) WHERE ROWNUM <= 10;

SELECT bbsID FROM BBS ORDER BY bbsID DESC;