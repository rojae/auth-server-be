-- auth.tbl_account definition

CREATE TABLE `tbl_account` (
                               `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `created_date` datetime(6) DEFAULT NULL,
                               `modified_date` datetime(6) DEFAULT NULL,
                               `email` varchar(255) CHARACTER SET utf8 NOT NULL,
                               `is_auth` char(1) NOT NULL,
                               `is_enable` char(1) NOT NULL,
                               `last_login_date` datetime(6) DEFAULT NULL,
                               `nickname` varchar(255) CHARACTER SET utf8 NOT NULL,
                               `password` varchar(255) NOT NULL,
                               `platform_type` varchar(10) NOT NULL,
                               `profile_image` varchar(255) DEFAULT NULL,
                               `req_uuid` varchar(255) NOT NULL,
                               PRIMARY KEY (`account_id`),
                               UNIQUE KEY `UK_bbkmr2fn78s42ol38ss6fdlbh` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


-- auth.tbl_account_info_history definition

CREATE TABLE `tbl_account_info_history` (
                                            `seq` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `created_date` datetime(6) DEFAULT NULL,
                                            `account_id` bigint(20) NOT NULL,
                                            `email` varchar(255) CHARACTER SET utf8 NOT NULL,
                                            `is_auth` char(1) NOT NULL,
                                            `is_enable` char(1) NOT NULL,
                                            `nickname` varchar(255) CHARACTER SET utf8 NOT NULL,
                                            `password` varchar(255) NOT NULL,
                                            `platform_type` varchar(10) NOT NULL,
                                            `profile_image` varchar(255) DEFAULT NULL,
                                            `req_uuid` varchar(255) NOT NULL,
                                            PRIMARY KEY (`seq`),
                                            UNIQUE KEY `UK_tnohr975lrwruhjc9kjwyk47o` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- auth.tbl_account_login_history definition

CREATE TABLE `tbl_account_login_history` (
                                             `seq` bigint(20) NOT NULL AUTO_INCREMENT,
                                             `created_date` datetime(6) DEFAULT NULL,
                                             `account_id` bigint(20) NOT NULL,
                                             `last_login_date` datetime(6) DEFAULT NULL,
                                             PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;


-- auth.tbl_mail_req definition

CREATE TABLE `tbl_mail_req` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `email` varchar(255) NOT NULL,
                                `expire_date` datetime(6) DEFAULT NULL,
                                `is_auth` char(1) DEFAULT 'N',
                                `is_enable` char(1) NOT NULL DEFAULT 'N',
                                `mail_type` varchar(20) NOT NULL,
                                `reg_date` datetime(6) NOT NULL,
                                `secret_key` varchar(255) DEFAULT NULL,
                                `send_date` timestamp NULL DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;


-- auth.tbl_custom definition

CREATE TABLE `tbl_custom` (
                              `account_id` bigint(20) NOT NULL,
                              `created_date` datetime(6) DEFAULT NULL,
                              `modified_date` datetime(6) DEFAULT NULL,
                              `agree_adult` varchar(255) NOT NULL,
                              `agree_personal_info` varchar(255) NOT NULL,
                              `agree_recv_mail` varchar(255) NOT NULL,
                              `agree_recv_sms` varchar(255) NOT NULL,
                              `birth_date` varchar(255) NOT NULL,
                              `gender` varchar(255) NOT NULL,
                              `mobile_tel1` varchar(255) NOT NULL,
                              `mobile_tel2` varchar(255) NOT NULL,
                              `mobile_tel3` varchar(255) NOT NULL,
                              `name` varchar(255) CHARACTER SET utf8 NOT NULL,
                              PRIMARY KEY (`account_id`),
                              CONSTRAINT `FK8y9ct5teox0axlplkt41730w5` FOREIGN KEY (`account_id`) REFERENCES `tbl_account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--------------------------------------
-------------- DATA ------------------
--------------------------------------
INSERT INTO tbl_account (account_id, created_date, modified_date, email, is_auth, is_enable, last_login_date, nickname, password, platform_type, profile_image, req_uuid)
VALUES (1, '2023-03-12 10:04:19.935', '2023-04-29 14:49:52.795', 'test@email.com', 'Y', 'Y', '2023-04-29 14:49:52.553', '테스트', '{bcrypt}$2a$10$1RFGi6X3zWeF4eKFx6i37.2N/2iBglzH19frEQbaOGruBy0jqn2Ku', 'NONSOCIAL', '', '');

INSERT INTO tbl_account (account_id, created_date, modified_date, email, is_auth, is_enable, last_login_date, nickname, password, platform_type, profile_image, req_uuid)
VALUES (2, '2023-03-12 10:04:19.935', '2023-04-29 14:49:52.795', 'test2@email.com', 'Y', 'Y', '2023-04-29 14:49:52.553', '테스트', '{bcrypt}$2a$10$1RFGi6X3zWeF4eKFx6i37.2N/2iBglzH19frEQbaOGruBy0jqn2Ku', 'NONSOCIAL', '', '');