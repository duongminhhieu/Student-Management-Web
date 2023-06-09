USE [StudentManagement Web ]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 14/04/2023 8:29:01 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[id] [nchar](10) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[lecture] [nvarchar](50) NOT NULL,
	[year] [int] NOT NULL,
	[note] [nvarchar](1000) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Enrollment]    Script Date: 14/04/2023 8:29:01 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Enrollment](
	[idStudent] [nchar](10) NOT NULL,
	[idCourse] [nchar](10) NOT NULL,
	[score] [float] NULL,
	[enrollment_date] [date] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 14/04/2023 8:29:01 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[id] [nchar](10) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[grade] [float] NOT NULL,
	[birthday] [date] NOT NULL,
	[address] [nvarchar](50) NOT NULL,
	[note] [nvarchar](1000) NOT NULL
) ON [PRIMARY]
GO
INSERT [dbo].[Course] ([id], [name], [lecture], [year], [note]) VALUES (N'MTH0002   ', N'TÂM LÝ ĐẠI CƯƠNG', N'Nguyễn Thị Kim Oanh', 2020, N'Trợ giảng: Dương Văn Hào')
INSERT [dbo].[Course] ([id], [name], [lecture], [year], [note]) VALUES (N'MTH00003  ', N'THIẾT KẾ PHẦN MỀM', N'Trần Quý', 2020, N'Trợ giảng: Dương Minh Hiếu')
INSERT [dbo].[Course] ([id], [name], [lecture], [year], [note]) VALUES (N'MTH11111  ', N'LẬP TRÌNH ỨNG DỤNG JAVA', N'Nguyễn Thanh Quảng', 2023, N'Trợ giảng: Dương Minh Hiếu')
INSERT [dbo].[Course] ([id], [name], [lecture], [year], [note]) VALUES (N'MTH12334  ', N'PHẦN MỀM GAME', N'2', 2022, N'Trợ giảng: Văn Long')
GO
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120456  ', N'MTH00003  ', 9, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120456  ', N'MTH0002   ', 7, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120473  ', N'MTH00003  ', 7.4000000953674316, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120473  ', N'MTH11111  ', 2.0999999046325684, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120474  ', N'MTH11111  ', 1.6000000238418579, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120555  ', N'MTH11111  ', 9, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120474  ', N'MTH0002   ', 6, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120555  ', N'MTH0002   ', 10, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120473  ', N'MTH0002   ', 7.5, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120474  ', N'MTH00003  ', 2.5, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120489  ', N'MTH00003  ', 10, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120555  ', N'MTH00003  ', 4.5999999046325684, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120456  ', N'MTH12334  ', 4, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120473  ', N'MTH12334  ', 6, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120474  ', N'MTH12334  ', 7, CAST(N'2023-04-14' AS Date))
INSERT [dbo].[Enrollment] ([idStudent], [idCourse], [score], [enrollment_date]) VALUES (N'20120489  ', N'MTH12334  ', 2, CAST(N'2023-04-14' AS Date))
GO
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120473  ', N'Trương Ngọc Thái', 2.9000000953674316, CAST(N'2002-08-20' AS Date), N'Bình Thanh, Bình Sơn, Quảng Ngãi', N'XInh đẹp, học giỏi vcl')
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120456  ', N'Dương Minh Hiếu', 2, CAST(N'2024-06-04' AS Date), N'Xóm 3, thôn An Quang', N'Học giỏi')
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120489  ', N'Hoàng văn hiêu', 0, CAST(N'2023-04-10' AS Date), N'Bình định', N'Học giỏi')
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120555  ', N'Trần Chí Nguyên', 0, CAST(N'2000-02-02' AS Date), N'An Giang', N'dd')
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120474  ', N'Trí Nguyên', 8, CAST(N'2002-02-01' AS Date), N'An Giang', N'Học không giỏi')
INSERT [dbo].[Student] ([id], [name], [grade], [birthday], [address], [note]) VALUES (N'20120479  ', N'Hoàng Văn Tồn', 0, CAST(N'2023-04-16' AS Date), N'BINH THANHBINH SON', N'Học giỏi')
GO
