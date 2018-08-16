CREATE TABLE [dbo].[bone_order](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NOT NULL,
	[year] [int] NOT NULL,
	[code] [nvarchar](50) NOT NULL,
	[region_no] [smallint] NOT NULL,
	[sync] [smallint] NOT NULL,
	[last_time] [datetime] NOT NULL,
	[yn] [smallint] NOT NULL,
 CONSTRAINT [PK_bone_order] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[bone_order] ADD  CONSTRAINT [DF_bone_order_year]  DEFAULT ((0)) FOR [year]
GO

ALTER TABLE [dbo].[bone_order] ADD  CONSTRAINT [DF_bone_order_region_no]  DEFAULT ((0)) FOR [region_no]
GO

ALTER TABLE [dbo].[bone_order] ADD  CONSTRAINT [DF_bone_order_sync]  DEFAULT ((0)) FOR [sync]
GO

ALTER TABLE [dbo].[bone_order] ADD  CONSTRAINT [DF_bone_order_yn]  DEFAULT ((1)) FOR [yn]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'create_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'编码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分区号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'region_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否同步（0 未同步 1已同步 2错误)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'sync'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最近更新时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order', @level2type=N'COLUMN',@level2name=N'last_time'
GO

/****** Object:  Index [IX_bone_order]    Script Date: 2015/11/1 11:44:23 ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_bone_order] ON [dbo].[bone_order]
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO

CREATE TABLE [dbo].[bone_order_detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [bigint] NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[gender] [smallint] NOT NULL,
	[birthday] [datetime] NOT NULL,
	[address_id] [bigint] NOT NULL,
	[yn] [smallint] NOT NULL,
 CONSTRAINT [PK_bone_order_detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[bone_order_detail] ADD  CONSTRAINT [DF_bone_order_detail_yn]  DEFAULT ((1)) FOR [yn]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'order_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'gender'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'生日' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'birthday'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'address_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否有效' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'bone_order_detail', @level2type=N'COLUMN',@level2name=N'yn'
GO

/****** Object:  Index [IX_bone_order_detail]    Script Date: 2015/11/9 15:51:43 ******/
CREATE NONCLUSTERED INDEX [IX_bone_order_detail] ON [dbo].[bone_order_detail]
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO