set identity_insert bone_order ON--打开
-- order data
INSERT INTO [dbo].[bone_order]([id],[create_time],[year],[code],[sync],[last_time],[yn])VALUES(1,getdate(),1997,'121',0,getdate(),1);
INSERT INTO [dbo].[bone_order]([id],[create_time],[year],[code],[sync],[last_time],[yn])VALUES(2,getdate(),1997,'12',0,getdate(),1);
set identity_insert bone_order OFF--关闭

set identity_insert bone_order_detail ON--打开
-- order detail data
INSERT INTO bone_order_detail([id],[order_id],[name],[gender],[birthday],[yn],[address_id])VALUES(1, 1,'Jim',1,'1980-01-09',1,1);
INSERT INTO bone_order_detail([id],[order_id],[name],[gender],[birthday],[yn],[address_id])VALUES(2, 1,'Mary',2,'1980-05-01',1,1);
INSERT INTO bone_order_detail([id],[order_id],[name],[gender],[birthday],[yn],[address_id])VALUES(3, 1,'Jack',1,'1980-02-09',1,1);

-- order detail data
INSERT INTO bone_order_detail([id],[order_id],[name],[gender],[birthday],[yn],[address_id])VALUES(4, 1,'Mary4Delete',2,'1980-05-01',0,2);
INSERT INTO bone_order_detail([id],[order_id],[name],[gender],[birthday],[yn],[address_id])VALUES(5, 1,'Jack4Delete',1,'1980-02-09',0,2);
set identity_insert bone_order_detail OFF--关闭
