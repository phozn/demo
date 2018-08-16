-- address data
set identity_insert bone_address ON--打开
INSERT INTO bone_address(id,address,zip,yn) VALUES (1, 'Hebei xingtai jiangshui', '054013', 1);
INSERT INTO bone_address(id,address,zip,yn) VALUES (2, 'Hebei xingtai zhenzhutao', '054013', 1);
set identity_insert bone_address OFF--关闭
