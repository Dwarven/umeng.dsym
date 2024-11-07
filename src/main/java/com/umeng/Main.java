package com.umeng;

import java.io.File;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.out.println("Usage:\n java -jar dsym.umeng.jar [access_key_id] [access_key_secret] [app_version] [datasource_id] [/path/to/xxx.app.dSYM.zip]");
            System.exit(1);
        }
        String access_key_id, access_key_secret, app_version, datasource_id, dsym_path;
        access_key_id = args[0];
        access_key_secret = args[1];
        app_version = args[2];
        datasource_id = args[3];
        dsym_path = args[4];
        File dsym_file = new File(dsym_path);
        if (!dsym_file.exists()) {
            System.out.println(dsym_path + " does not exist!");
            System.exit(1);
        }
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(access_key_id)
                .setAccessKeySecret(access_key_secret);
        config.endpoint = "apm.openapi.umeng.com";
        com.aliyun.umeng_apm20220214.Client client = new com.aliyun.umeng_apm20220214.Client(config);
        client._openPlatformEndpoint = "apm.openapi.umeng.com";
        com.aliyun.umeng_apm20220214.models.UploadSymbolFileAdvanceRequest request = new com.aliyun.umeng_apm20220214.models.UploadSymbolFileAdvanceRequest();
        request.setAppVersion(app_version)
                .setDataSourceId(datasource_id)
                .setFileName(dsym_file.getName())
                .setFileType(3)
                .setOssUrlObject(Files.newInputStream(dsym_file.toPath()));
        java.util.Map<String, String> headers = new java.util.HashMap<>();
        try {
            com.aliyun.umeng_apm20220214.models.UploadSymbolFileResponse response = client.uploadSymbolFileAdvance(request, headers, new com.aliyun.teautil.models.RuntimeOptions());
            com.google.gson.Gson gson = new com.google.gson.Gson();
            System.out.println(gson.toJson(response.toMap()));
        } catch (com.aliyun.tea.TeaException error) {
            System.out.println(error.message);
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            com.aliyun.tea.TeaException error = new com.aliyun.tea.TeaException(_error.getMessage(), _error);
            System.out.println(error.message);
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }
}