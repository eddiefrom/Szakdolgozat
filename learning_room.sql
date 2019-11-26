-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2019. Ápr 23. 21:41
-- Kiszolgáló verziója: 10.1.31-MariaDB
-- PHP verzió: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `learning_room`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `competitionresults`
--

CREATE TABLE `competitionresults` (
  `id` bigint(20) NOT NULL,
  `exerciseID` bigint(20) DEFAULT NULL,
  `gotPoint` double DEFAULT NULL,
  `gotTime` int(11) DEFAULT NULL,
  `types` int(11) DEFAULT NULL,
  `worksheetID` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `exercise`
--

CREATE TABLE `exercise` (
  `id` bigint(20) NOT NULL,
  `difficultyLevel` int(11) DEFAULT NULL,
  `exerciseCat` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `goodAnswer` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `help` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `point` double NOT NULL,
  `time` int(11) NOT NULL,
  `types` int(11) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `worksheet_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `exercise`
--

INSERT INTO `exercise` (`id`, `difficultyLevel`, `exerciseCat`, `goodAnswer`, `help`, `name`, `point`, `time`, `types`, `url`, `worksheet_id`) VALUES
(1, 0, 'Geometria', 'sRuXE9jyCA6WItHGMYlRaQ==', '', 'halmaz_1', 10, 40, 0, '.\\examples\\halmaz_1.png', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `parent`
--

CREATE TABLE `parent` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profilPic` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `parent`
--

INSERT INTO `parent` (`id`, `email`, `name`, `password`, `profilPic`) VALUES
(1, 'nagy_erzsi@gmail.com', 'Nagyné Erzsébet', 'i5P2odlaLBpqJZLlsuqGZQ==', NULL),
(2, 'kis_juli@gmail.com', 'Kisné Julianna', 'i5P2odlaLBpqJZLlsuqGZQ==', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `practiceresults`
--

CREATE TABLE `practiceresults` (
  `id` bigint(20) NOT NULL,
  `exerciseID` bigint(20) DEFAULT NULL,
  `gotPoint` double DEFAULT NULL,
  `gotTime` int(11) DEFAULT NULL,
  `types` int(11) DEFAULT NULL,
  `worksheetID` bigint(20) DEFAULT NULL,
  `studentP_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `student`
--

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `allPoints` double DEFAULT NULL,
  `bornDate` date DEFAULT NULL,
  `classNumber` int(11) NOT NULL,
  `competitionTime` int(11) NOT NULL,
  `easyPoints` double DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `fromMindExercise` double DEFAULT NULL,
  `hardPoints` double DEFAULT NULL,
  `largestTime` int(11) NOT NULL,
  `mainExercise` double DEFAULT NULL,
  `mediumPoints` double DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `practiceTime` int(11) NOT NULL,
  `profilPicURL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `shortestTime` int(11) NOT NULL,
  `trueFalseExercise` double DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `student`
--

INSERT INTO `student` (`id`, `age`, `allPoints`, `bornDate`, `classNumber`, `competitionTime`, `easyPoints`, `email`, `fromMindExercise`, `hardPoints`, `largestTime`, `mainExercise`, `mediumPoints`, `name`, `password`, `practiceTime`, `profilPicURL`, `shortestTime`, `trueFalseExercise`, `parent_id`) VALUES
(1, 26, 0, '1993-02-01', 8, 0, 0, 'k_istvan@gmail.com', 0, 0, 0, 0, 0, 'Kis István', 'i5P2odlaLBpqJZLlsuqGZQ==', 0, NULL, 0, 0, 2),
(2, 27, 0, '1991-08-18', 10, 0, 0, 'n_laszlo@gmail.com', 0, 0, 0, 0, 0, 'Nagy László', 'i5P2odlaLBpqJZLlsuqGZQ==', 0, NULL, 0, 0, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `student_teacher`
--

CREATE TABLE `student_teacher` (
  `studentList_id` bigint(20) NOT NULL,
  `teachers_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `student_teacher`
--

INSERT INTO `student_teacher` (`studentList_id`, `teachers_id`) VALUES
(1, 1),
(2, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `teacher`
--

CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `profilPicURL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userName` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `teacher`
--

INSERT INTO `teacher` (`id`, `email`, `name`, `password`, `profilPicURL`, `userName`) VALUES
(1, 'tanar@gmail.com', 'Kovács János', 'i5P2odlaLBpqJZLlsuqGZQ==', NULL, 'kJani');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `worksheet`
--

CREATE TABLE `worksheet` (
  `id` bigint(20) NOT NULL,
  `categories` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `classNumber` int(11) NOT NULL,
  `creator` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `difficultyLevel` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `points` double NOT NULL,
  `types` varchar(255) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- A tábla adatainak kiíratása `worksheet`
--

INSERT INTO `worksheet` (`id`, `categories`, `classNumber`, `creator`, `difficultyLevel`, `name`, `points`, `types`) VALUES
(1, 'Geometria', 10, 'Kovács János', 0, 'elsö', 10, 'Általános');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `worksheet_student`
--

CREATE TABLE `worksheet_student` (
  `worksheetList_id` bigint(20) NOT NULL,
  `studentList_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `competitionresults`
--
ALTER TABLE `competitionresults`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKr9dckngmy7thplsdauaj95l7d` (`student_id`);

--
-- A tábla indexei `exercise`
--
ALTER TABLE `exercise`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8lr1rj9qakkoful8odvigf2gh` (`worksheet_id`);

--
-- A tábla indexei `parent`
--
ALTER TABLE `parent`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `practiceresults`
--
ALTER TABLE `practiceresults`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqnf1jdsthel7avirfbdevq8u8` (`studentP_id`);

--
-- A tábla indexei `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa6lko0jeoc3s9ismxnhx3hfxw` (`parent_id`);

--
-- A tábla indexei `student_teacher`
--
ALTER TABLE `student_teacher`
  ADD KEY `FK3is203tn75s2rf6h3oeld95fy` (`teachers_id`),
  ADD KEY `FKarigjjuvighg9xp2bv249pk1o` (`studentList_id`);

--
-- A tábla indexei `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `worksheet`
--
ALTER TABLE `worksheet`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `worksheet_student`
--
ALTER TABLE `worksheet_student`
  ADD KEY `FKhvo4i1lyh8i4qqt2dhq686ii7` (`studentList_id`),
  ADD KEY `FKnw96p0n6howioj220sf9ispa9` (`worksheetList_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `competitionresults`
--
ALTER TABLE `competitionresults`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `exercise`
--
ALTER TABLE `exercise`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `parent`
--
ALTER TABLE `parent`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `practiceresults`
--
ALTER TABLE `practiceresults`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `student`
--
ALTER TABLE `student`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `worksheet`
--
ALTER TABLE `worksheet`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `competitionresults`
--
ALTER TABLE `competitionresults`
  ADD CONSTRAINT `FKr9dckngmy7thplsdauaj95l7d` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `exercise`
--
ALTER TABLE `exercise`
  ADD CONSTRAINT `FK8lr1rj9qakkoful8odvigf2gh` FOREIGN KEY (`worksheet_id`) REFERENCES `worksheet` (`id`);

--
-- Megkötések a táblához `practiceresults`
--
ALTER TABLE `practiceresults`
  ADD CONSTRAINT `FKqnf1jdsthel7avirfbdevq8u8` FOREIGN KEY (`studentP_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `FKa6lko0jeoc3s9ismxnhx3hfxw` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`id`);

--
-- Megkötések a táblához `student_teacher`
--
ALTER TABLE `student_teacher`
  ADD CONSTRAINT `FK3is203tn75s2rf6h3oeld95fy` FOREIGN KEY (`teachers_id`) REFERENCES `teacher` (`id`),
  ADD CONSTRAINT `FKarigjjuvighg9xp2bv249pk1o` FOREIGN KEY (`studentList_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `worksheet_student`
--
ALTER TABLE `worksheet_student`
  ADD CONSTRAINT `FKhvo4i1lyh8i4qqt2dhq686ii7` FOREIGN KEY (`studentList_id`) REFERENCES `student` (`id`),
  ADD CONSTRAINT `FKnw96p0n6howioj220sf9ispa9` FOREIGN KEY (`worksheetList_id`) REFERENCES `worksheet` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
