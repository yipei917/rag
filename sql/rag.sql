/*
 Navicat Premium Data Transfer

 Source Server         : pei
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : rag

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 11/05/2024 13:10:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '聊天id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '聊天标题',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES (1, 'title', 1);
INSERT INTO `chat` VALUES (2, 'chat2', 1);
INSERT INTO `chat` VALUES (89, 'title', 1);
INSERT INTO `chat` VALUES (90, 'title', 1);
INSERT INTO `chat` VALUES (91, 'title', 1);
INSERT INTO `chat` VALUES (92, 'title', 1);

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '知识编码',
  `kb_id` bigint NULL DEFAULT NULL COMMENT '知识库id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `status` int NOT NULL COMMENT '状态',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `knowledge-knowledgeBase`(`kb_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of knowledge
-- ----------------------------
INSERT INTO `knowledge` VALUES (1, 'qq-1', 1, 'rag是什么', '众所周知，LLM是一个预训练的模型，这就决定了LLM自身无法实时更新模型中的知识，正因为如此，所以现在业界已经形成了通过RAG（Retrieval Augmented Generation）等外接知识库等方式是快速扩展LLM知识的方式，也是把内部资产快速接入模型形成应用的共识。', 1, '2024-04-15 15:35:22', NULL);
INSERT INTO `knowledge` VALUES (2, 'qq-2', 1, 'rag的发展历程', 'RAG的概念是2020年提出的[2]，第一次将预训练的检索器与预训练的seq2seq模型相结合，并进行端到端的微调。真正发扬光大是在2022年后，特别是ChatGPT的提出，NLP迈入大模型时代。\r\n\r\n很多人不知道，RAG从诞生开始一直是研究预训练和SFT的，推理截断的RAG是在LLM时代后才开始井喷。因为高性能大模型的训练成本高，学术界和工业级试图通过推理阶段加入RAG模块，高性价比的方式整合外部知识来增强模型生成。RAG的检索范围也在逐步放开，早起的RAG重点关注开源的，非结构化知识；随着检索范围的扩大，高质量数据作为知识来源，逐步在减轻LLM大模型的错误知识和幻觉问题。最近也有很多关于知识图谱，自我检索等方向的研究，跨学科和LLM进行结合的探索。', 1, '2024-04-15 15:35:59', NULL);
INSERT INTO `knowledge` VALUES (3, 'hh-1', 1, '测试', '为空', 1, '2024-04-28 11:21:30', NULL);
INSERT INTO `knowledge` VALUES (4, 'hh-2', 1, '不为空', '测试', 1, '2024-04-28 11:21:52', NULL);
INSERT INTO `knowledge` VALUES (7, 'test-1', 12, '厦门的相关介绍', '厦门市，简称厦或鹭，位于中国华东地区、福建省东南部沿海，地形以滨海平原、台地和丘陵为主，属南亚热带海洋性季风气候，温和多雨，截至2023年10月，总面积1700.61平方千米，截至2023年末，厦门市常住人口532.70万人，常住人口城镇化率90.81%。1935年，设立厦门市，1980年，经国务院批准设立厦门经济特区。厦门市是国务院批复确定的中国经济特区和东南沿海重要的中心城市、港口及风景旅游城市，2023年，厦门市实现地区生产值8066.49亿元，其中，第一产业增加值27.73亿元，第二产业增加值2867.94亿元，第三产业增加值5170.81亿元', 1, '2024-04-29 18:53:30', NULL);

-- ----------------------------
-- Table structure for knowledge_base
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_base`;
CREATE TABLE `knowledge_base`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int NOT NULL COMMENT '状态',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of knowledge_base
-- ----------------------------
INSERT INTO `knowledge_base` VALUES (1, NULL, 'qq', '11', 1, 1, '2024-03-18 14:02:04', '2024-04-15 23:24:07');
INSERT INTO `knowledge_base` VALUES (12, '测试向量数据库', 'test', '这是一段描述。', 1, 1, '2024-04-29 17:19:44', NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `role` int NOT NULL COMMENT '0：system、1：assistant、2：user',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `chat_id` bigint NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (3, 2, '请发一段c++的代码', NULL, NULL);
INSERT INTO `message` VALUES (4, 1, '当然可以。以下是一个简单的 C++ 程序示例，该程序会输出 \"Hello, World!\" 到控制台：\n\n```cpp\n#include <iostream>\n\nint main() {\n    // 输出 \"Hello, World!\" 到控制台\n    std::cout << \"Hello, World!\" << std::endl;\n    return 0;\n}\n```\n\n这段代码展示了 C++ 程序的基本结构：\n\n1. `#include <iostream>`：这是预处理指令，它告诉编译器包含标准输入输出流库，这样你就可以使用 `std::cout` 进行输出。\n\n2. `int main()`：这是主函数，每个 C++ 程序都必须有一个名为 `main` 的函数，程序的执行从这里开始。\n\n3. `std::cout << \"Hello, World!\" << std::endl;`：这行代码实现了输出 \"Hello, World!\" 到控制台。`<<` 运算符用于流插入，`std::endl` 是一个特殊的字符，用于在输出流中插入一个换行符并刷新输出缓冲区。\n\n4. `return 0;`：这表示程序成功执行完毕。在 `main` 函数中，返回 0 通常表示程序正常结束。\n\n要运行这段代码，你需要一个 C++ 编译器，如 GCC、Clang 或者 MSVC。将代码保存为一个以 `.cpp` 结尾的文件，然后使用编译器编译和运行它。', NULL, NULL);

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模型代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int NOT NULL COMMENT '状态',
  `product` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名',
  `rule` bigint NULL DEFAULT NULL COMMENT '计费规则',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of model
-- ----------------------------

-- ----------------------------
-- Table structure for prompt
-- ----------------------------
DROP TABLE IF EXISTS `prompt`;
CREATE TABLE `prompt`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `status` int NOT NULL COMMENT '状态',
  `system_prompt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_prompt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `model_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `gmt_create` datetime NULL DEFAULT NULL,
  `gmt_modified` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `model`(`model_id` ASC) USING BTREE,
  INDEX `user`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prompt
-- ----------------------------
INSERT INTO `prompt` VALUES (1, 'name', 'code', 1, '请根据要求回答', 'default', 1, 1, '2024-05-03 11:56:15', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `type` int NOT NULL COMMENT '0：管理员、1：用户',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', 'test', 1, NULL);
INSERT INTO `user` VALUES (41, 'test', 'test', 1, NULL);
INSERT INTO `user` VALUES (42, 'test999', 'laborecillum', 1, 'molli');
INSERT INTO `user` VALUES (43, 'test99', 'laborecillum', 1, 'molli');
INSERT INTO `user` VALUES (44, 'test9', 'laborecillum', 1, 'molli');
INSERT INTO `user` VALUES (45, 'test8', 'laborecillum', 1, 'molli');
INSERT INTO `user` VALUES (46, 'test7', 'laborecillum', 1, 'molli');

SET FOREIGN_KEY_CHECKS = 1;
