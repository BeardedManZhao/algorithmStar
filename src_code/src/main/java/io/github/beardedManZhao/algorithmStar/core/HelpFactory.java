package io.github.beardedManZhao.algorithmStar.core;

import io.github.beardedManZhao.algorithmStar.algorithm.OperationAlgorithmManager;
import zhao.gravel.grammar.command.ActuatorAliasParam;
import zhao.gravel.grammar.command.GrammarParam;
import zhao.gravel.grammar.command.SaveParam;
import zhao.gravel.grammar.core.CommandCallback;
import zhao.utils.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * 帮助信息工厂类，在这里您可以获取到一些写好了的帮助信息
 *
 * @author zhao
 */
public class HelpFactory {

    /**
     * 获取到所有有关 AS 机器学习库的信息
     * <p>
     * Obtain all information about the AS machine learning library
     */
    public static final String ALL = "http://diskmirror.lingyuzhao.top/23/Binary/algorithmStar_doc/algorithmStar-Document" + OperationAlgorithmManager.VERSION + ".pdf";

    /**
     * 备用的 AS 机器学习库信息获取，与 ALL 一样 但是做为一个备选，当 ALL 无法使用的情况下可以调用
     * <p>
     * Backup AS machine learning library information acquisition, similar to ALL, but as an alternative, can be called when ALL is unavailable
     */
    public static final String ALL_BACK = "https://github.com/BeardedManZhao/algorithmStar/releases/download/" + OperationAlgorithmManager.VERSION + "/algorithmStar-Document" + OperationAlgorithmManager.VERSION + ".pdf";

    // 构造语法树
    private static final CommandCallback help;

    static {
        help = CommandCallback.createGet("\\s+");
        final HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        // 准备搜索组件
        help.addSubSyntax(
                GrammarParam.create(
                        "AlgorithmStar",
                        SaveParam.create("type", objectObjectHashMap,
                                new ActuatorAliasParam(CommandCallback.WILDCARD) {
                                    @Override
                                    public Object run() {
                                        // 获取到 URL 中的搜索 参数
                                        final Object orDefault = objectObjectHashMap.get("type");
                                        if (orDefault == null) {
                                            throw new UnsupportedOperationException("无法处理您提供的帮助类型!");
                                        }
                                        // 开始进行搜索
                                        try {
                                            final URL url = new URL((String) orDefault);
                                            return url.openStream();
                                        } catch (FileNotFoundException e) {
                                            throw new UnsupportedOperationException("帮助文档的链接似乎失效了！您可以给 liming7887@qq.com 发送邮件进行咨询!", e);
                                        } catch (IOException e) {
                                            throw new UnsupportedOperationException(orDefault.toString(), e);
                                        }
                                    }
                                })
                )
        );
    }

    /**
     * 获取到帮助信息的文件，并保存到您的目录中!
     *
     * @param type   帮助信息的类型
     * @param outDir 帮助信息的输出目录
     * @return 保存成功之后返回保存好的文件的路径
     * @throws UnsupportedOperationException 当获取到的类型不存在 或者 无法获取到帮助信息的时候，抛出此异常
     */
    public String saveHelpFile(String type, String outDir) {
        try {
            final String path = outDir + "/algorithmStar-Document" + OperationAlgorithmManager.VERSION + ".pdf";
            try {
                IOUtils.copy(
                        (InputStream) help.run("AlgorithmStar", "type", type),
                        new FileOutputStream(path),
                        true
                );
            } catch (UnsupportedOperationException e) {
                IOUtils.copy(
                        (InputStream) help.run("AlgorithmStar", "type", ALL_BACK),
                        new FileOutputStream(path),
                        true
                );
            }
            return path;
        } catch (IOException e) {
            throw new UnsupportedOperationException("帮助信息获取失败!", e);
        }
    }
}
