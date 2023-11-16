package com.lazycoder.codeformat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        File file = new File("H:\\工作\\临时工作\\20230606添加seq\\最终sql\\subject_data_graph.sql");

        String[] subject_data_graph_list = new String[]{
                "tb_graph_edge_address_book", "tb_graph_edge_alipay_alipay", "tb_graph_edge_alipay_card",
                "tb_graph_edge_case_collect", "tb_graph_edge_case_person", "tb_graph_edge_case_phone",
                "tb_graph_edge_collect_msisdn", "tb_graph_edge_collect_qq", "tb_graph_edge_collect_wechat",
                "tb_graph_edge_idcard_idcard", "tb_graph_tag_alipay", "tb_graph_tag_debit", "tb_graph_tag_tenpay",
                "tb_graph_edge_call", "tb_graph_edge_card_alipay", "tb_graph_edge_bank_card",
                "tb_graph_edge_card_tenpay", "tb_graph_edge_mms", "tb_graph_edge_qq_chat",
                "tb_graph_edge_qq_friends", "tb_graph_edge_relatives", "tb_graph_edge_sms_contact",
                "tb_graph_edge_tenpay_card", "tb_graph_edge_tenpay_tenpay", "tb_graph_edge_use_alipay",
                "tb_graph_edge_use_car", "tb_graph_edge_use_card", "tb_graph_edge_use_collect",
                "tb_graph_edge_use_msisdn", "tb_graph_edge_use_tenpay", "tb_graph_edge_use_wechat",
                "tb_graph_edge_wechat_chat", "tb_graph_edge_wechat_friends", "tb_graph_tag_collect",
                "tb_graph_tag_case", "tb_graph_tag_car", "tb_graph_tag_idcard", "tb_graph_tag_wechat",
                "tb_graph_tag_qq", "tb_graph_tag_msisdn"
        };
//		String[] subject_data_tb_list = new String[]{
//				"tb_car_track_gather","tb_cell_call_gather","tb_personid_gather_1","tb_cell_action_gather","tb_personid_gather","tb_cell_track_gather","tb_transfer_gather"
//		};
//		String[] original_data_tb_list = new String[]{
//				"gsd_lyxt_jnlkzs","gsd_jdckk_passcar","csd_dzwl_wfcj","csd_dzwl_wxcj","tb_wa_mforensics_010100",
//				"tb_fn_v_cggk_yjxx_450422","t_emphasis_field_value","tb_sjcj_wa_mforensics_030300",
//				"dl_t_gxzx_zhyqzgxb","t_empowerment_delivery","tb_ch_t_cggk_yjxx_450422",
//				"tb_ch_t_original_data_jszxx","tb_sjcj_wa_mforensics_060200","tb_sjcj_wa_mforensics_020200",
//				"tb_sjcj_wa_mforensics_020300","tb_sjcj_wa_mforensics_070100","tb_sjcj_wa_mforensics_110300",
//				"dl_t_gxzx_zjhmskfbmxb","tb_sjcj_wa_mforensics_070600","tb_sjcj_wa_mforensics_030500",
//				"tb_sjcj_wa_mforensics_010201","tb_ch_t_original_data_info_kettle","tb_sjcj_wa_mforensics_070700",
//				"tb_ch_t_original_data_estate","dl_t_gxzx_yszdxwb","tb_sjcj_wa_mforensics_090500","dw_t_gxzx_shdw",
//				"dw_t_gxzx_ryztb","tb_sjcj_wa_mforensics_050200","tb_sjcj_wa_mforensics_070200",
//				"tb_sjcj_wa_mforensics_010600","tb_sjcj_wa_mforensics_020500","tb_sjcj_wa_mforensics_030400",
//				"tb_sjcj_wa_mforensics_070800","tb_sjcj_wa_mforensics_030200","tb_ch_t_original_data_virtual_acc",
//				"dl_t_gxzx_txlgxb","tb_sjcj_wa_mforensics_020800","tb_ch_t_original_data_jdcxx",
//				"tb_ch_t_lawcase_saxyr","tb_sjcj_wa_mforensics_090300","tb_ch_t_original_data_tubutype",
//				"tb_sjcj_wa_mforensics_050600","tb_ch_t_original_data_tlspxx","tb_ch_t_original_data_info",
//				"tb_ch_t_original_data_guardian","dw_t_gxzx_xsajztb","tb_sjcj_wa_mforensics_010202",
//				"tb_ch_t_original_data_info_copy1","tb_sjcj_wa_mforensics_011200","dw_t_gxzx_qdcjsb",
//				"tb_sjcj_wa_mforensics_040100","tb_sjcj_wa_mforensics_110100","tb_sjcj_wa_mforensics_060100",
//				"tb_sjcj_wa_mforensics_011000","tb_ch_t_original_data_tubulation","tb_ch_t_original_data_phone_kettle",
//				"tb_ch_t_original_data_phone","tb_ch_t_original_data_photo","t_empowerment_customercall",
//				"dl_t_gxzx_ragx","tb_ch_t_original_data_info_group","tb_ch_t_call_record","tb_sjcj_wa_mforensics_050300",
//				"t_empowerment_lbsres","t_empowerment_customerres","dl_t_gxzx_wmhygxb","tb_sjcj_wa_mforensics_070500",
//				"tb_sjcj_wa_mforensics_040200","tb_ch_t_original_data_image","tb_sjcj_wa_mforensics_050100",
//				"tb_ch_t_original_data_info_1122","tb_sjcj_wa_mforensics_020600","tb_ch_t_original_data_jzzxx",
//				"tb_sjcj_wa_mforensics_090400","dl_t_gxzx_cphmskfbmxb","tb_sjcj_wa_mforensics_020400",
//				"tb_sjcj_wa_mforensics_010100","tb_sjcj_wa_mforensics_090600","tb_sjcj_wa_mforensics_050400",
//				"tb_sjcj_wa_mforensics_010800","ods_t_gxzx_za_zdry_idx_jxxx","tb_jz_v_empapp_info","dw_t_gxzx_xzajztb",
//				"tb_sjcj_wa_mforensics_050500","dw_t_gxzx_clztb","tb_sjcj_wa_mforensics_040300",
//				"tb_sjcj_wa_mforensics_010400","tb_sjcj_wa_mforensics_080100","tb_sjcj_wa_mforensics_160100",
//				"tb_sjcj_wa_mforensics_010700","tb_ch_t_original_data_czrk","t_empowerment_lbs",
//				"tb_sjcj_wa_mforensics_010500","tb_sjcj_wa_mforensics_010900","dl_t_gxzx_sjkhskfbmxb",
//				"tb_ch_t_original_data_group","tb_ch_t_bcp_log","tb_jz_v_empapp_phone","tb_ch_t_original_data_wbry",
//				"tb_sjcj_wa_mforensics_011100","tb_sjcj_wa_mforensics_020700","gsd_jdckk_kkxx",
//				"tb_sjcj_wa_mforensics_010200","tb_sjcj_wa_mforensics_060400","tb_sjcj_wa_mforensics_110200",
//				"dl_t_gxzx_xnsfglb","tb_sjcj_wa_mforensics_090100","dl_t_gxzx_sjsmglb","dl_t_gxzx_ysgljcb",
//				"tb_ch_t_original_data_info_label","dw_t_gxzx_wxcs","tb_sjcj_wa_mforensics_020100",
//				"tb_sjcj_wa_mforensics_109999","tb_ch_t_lawcase_wcnsadj","t_empowerment_customer",
//				"tb_sjcj_wa_mforensics_080200","tb_ch_t_original_data_info_group_kettle","tb_sjcj_wa_mforensics_090200",
//				"tb_sjcj_wa_mforensics_030100","tb_sjcj_wa_mforensics_010300","t_empowerment_deliveryres",
//				"dl_t_gxzx_yszdnrb","v_original_data_info"
//		};
//		String[] resource_data_tb_list = new String[]{
//				"tb_account_bank","tb_account_msisdn","tb_alarm_deployed","tb_alarm_mission",
//				"tb_alarm_mission_message","tb_alarm_mission_model","tb_alarm_mission_model_message",
//				"tb_alarm_model_def","tb_alarm_policeman","tb_alarm_score","tb_alarm_sms",
//				"tb_bcp_track","tb_bill_file","tb_bill_file_detail","tb_case","tb_cdr_behavior",
//				"tb_cdr_file","tb_cdr_file_detail","tb_focus_groups_info","tb_focus_groups_phone",
//				"tb_mb_report_app_friends","tb_mb_report_app_group","tb_mb_report_behavior_analysis",
//				"tb_mb_report_call_records","tb_mb_report_device_connection","tb_mb_report_email",
//				"tb_mb_report_financial_sp","tb_mb_report_kat","tb_mb_report_list",
//				"tb_mb_report_microblog","tb_mb_report_sms_records","tb_mb_report_statistics",
//				"tb_mb_report_statistics_tpl","tb_mb_report_transportation_tel","tb_mb_report_web_history",
//				"tb_mb_warning_statistics","tb_mb_warning_statistics_tpl","tb_msisdn_confidence",
//				"tb_pef_add_id_card","tb_pef_certificate_gather","tb_pef_close_contacts","tb_pef_country_fugitive",
//				"tb_pef_driver_license_info","tb_pef_electricity_user_registr","tb_pef_floating_population",
//				"tb_pef_household_info","tb_pef_internet_cafe_internet_p","tb_pef_man_rel_gather",
//				"tb_pef_passenger_accommodation","tb_pef_person_alarm","tb_pef_person_avatar",
//				"tb_pef_person_bank_card_gather","tb_pef_person_case_gather","tb_pef_person_criminal_gather",
//				"tb_pef_person_enterprise_gather","tb_pef_person_family","tb_pef_person_images_gather",
//				"tb_pef_person_info","tb_pef_person_info_gather","tb_pef_person_medical_gather",
//				"tb_pef_person_vehicle_gather","tb_pef_person_venue_gather","tb_pef_phone_number_info",
//				"tb_pef_phone_number_info_gather","tb_pef_province_vehicle_info",
//				"tb_pef_province_vehicle_info_gather","tb_pef_residence_permit_info","tb_pef_resident_population",
//				"tb_pef_seven_level_key_person","tb_pef_social_accounts_gather","tb_pef_tags_gather",
//				"tb_pef_tapwater_payment_info","tb_pef_tapwater_user_info","tb_pef_thing_gather","tb_pef_track_behavior",
//				"tb_pef_track_distribution","tb_pef_track_same_station","tb_station_info","tb_sync_index_queue",
//				"tb_tag_edge","tb_tag_edge_rule","tb_tag_lib","tb_track_caac_booking_info","tb_track_passenger_entry_exit_r",
//				"tb_track_railway_ticket_info","tb_wa_020300_sum","tb_wa_mforensics_010100",
//				"tb_wa_mforensics_010200","tb_wa_mforensics_010201","tb_wa_mforensics_010202","tb_wa_mforensics_010300",
//				"tb_wa_mforensics_010400","tb_wa_mforensics_010500","tb_wa_mforensics_010600","tb_wa_mforensics_010700",
//				"tb_wa_mforensics_010800","tb_wa_mforensics_010900","tb_wa_mforensics_011000","tb_wa_mforensics_011100",
//				"tb_wa_mforensics_011200","tb_wa_mforensics_020100","tb_wa_mforensics_020200","tb_wa_mforensics_020300",
//				"tb_wa_mforensics_020400","tb_wa_mforensics_020500","tb_wa_mforensics_020600","tb_wa_mforensics_020700",
//				"tb_wa_mforensics_020800","tb_wa_mforensics_030100","tb_wa_mforensics_030200","tb_wa_mforensics_030300",
//				"tb_wa_mforensics_030400","tb_wa_mforensics_030500","tb_wa_mforensics_040100","tb_wa_mforensics_040200",
//				"tb_wa_mforensics_040300","tb_wa_mforensics_050100","tb_wa_mforensics_050200","tb_wa_mforensics_050300",
//				"tb_wa_mforensics_050400","tb_wa_mforensics_050500","tb_wa_mforensics_050600","tb_wa_mforensics_060100",
//				"tb_wa_mforensics_060200","tb_wa_mforensics_060400","tb_wa_mforensics_070100","tb_wa_mforensics_070200",
//				"tb_wa_mforensics_070500","tb_wa_mforensics_070600","tb_wa_mforensics_070700","tb_wa_mforensics_070800",
//				"tb_wa_mforensics_080100","tb_wa_mforensics_080200","tb_wa_mforensics_090100","tb_wa_mforensics_090200",
//				"tb_wa_mforensics_090300","tb_wa_mforensics_090400","tb_wa_mforensics_090500","tb_wa_mforensics_090600",
//				"tb_wa_mforensics_109999","tb_wa_mforensics_110100","tb_wa_mforensics_110200","tb_wa_mforensics_110300",
//				"tb_wa_mforensics_160100","tb_warning_def"
//		};


        //生成9大字段
//		String ori_sql = "" +
//				"---- 【tableName】"+
//				"---------------------------------------\n"+
//				"-- omci_src_table:    运维自定义 来源表\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_src_table;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_src_table varchar(50) NOT NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_src_table IS '运维自定义 来源表';\n" +
//				"\n" +
//				"-- omci_src_pkid:    运维自定义 来源表主键id\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_src_pkid;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_src_pkid varchar(50) NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_src_pkid IS '运维自定义 来源表主键id';\n" +
//				"\n" +
//				"-- omci_update_time：\t运维自定义 更新时间\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_update_time;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_update_time timestamp NOT NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_update_time IS '运维自定义 更新时间';\n" +
//				"\n" +
//				"-- omci_src_table_name:    运维自定义 来源表名称\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_src_table_name;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_src_table_name varchar(50) NOT NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_src_table_name IS '运维自定义 来源表名称';\n" +
//				"\n" +
//				"-- omci_seq:\t运维自定义 序列自增\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_seq;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_seq int8 NOT NULL GENERATED ALWAYS AS IDENTITY;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_seq IS '运维自定义 序列自增';\n" +
//				"\n" +
//				"--omci_del_time:\t运维自定义 删除时间\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_del_time;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_del_time timestamp NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_del_time IS '运维自定义 删除时间';\n" +
//				"\n" +
//				"--omci_del_state:\t运维自定义 删除状态(0:正常,1:已删除)     必填\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_del_state;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_del_state int2 NOT NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_del_state IS '运维自定义 删除状态(0:正常,1:已删除)';\n" +
//				"\n" +
//				"--omci_level:\t运维自定义 更新优先级(高级覆盖低级)\n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_level;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_level int2 NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_level IS '运维自定义 更新优先级(高级覆盖低级)';\n" +
//				"\n" +
//				"--omci_id:\t运维自定义 主键 \n" +
//				"ALTER TABLE 【tableName】 DROP COLUMN omci_id;\n" +
//				"ALTER TABLE 【tableName】 ADD omci_id varchar(50) NOT NULL;\n" +
//				"COMMENT ON COLUMN 【tableName】.omci_id IS '运维自定义 主键';" +
//				"---------------------------------------"+
//				"\n\n\n\n";

        //只生成seq字段
//        String ori_sql = "" +
//                "---- 【tableName】\n" +
//                "---------------------------------------\n" +
//                "-- omci_seq:\t运维自定义 序列自增\n" +
//                "ALTER TABLE 【tableName】 DROP COLUMN omci_seq;\n" +
//                "ALTER TABLE 【tableName】 ADD omci_seq int8 NOT NULL GENERATED ALWAYS AS IDENTITY;\n" +
//                "COMMENT ON COLUMN 【tableName】.omci_seq IS '运维自定义 序列自增';" +
//                "---------------------------------------\n" +
//                "\n\n\n\n";

		//生成8大字段 (不包括seq)
		String ori_sql = "" +
				"---- 【tableName】"+
				"---------------------------------------\n"+
				"-- omci_src_table:    运维自定义 来源表\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_src_table;\n" +
				"ALTER TABLE 【tableName】 ADD omci_src_table varchar(50) NOT NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_src_table IS '运维自定义 来源表';\n" +
				"\n" +
				"-- omci_src_pkid:    运维自定义 来源表主键id\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_src_pkid;\n" +
				"ALTER TABLE 【tableName】 ADD omci_src_pkid varchar(50) NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_src_pkid IS '运维自定义 来源表主键id';\n" +
				"\n" +
				"-- omci_update_time：\t运维自定义 更新时间\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_update_time;\n" +
				"ALTER TABLE 【tableName】 ADD omci_update_time timestamp NOT NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_update_time IS '运维自定义 更新时间';\n" +
				"\n" +
				"-- omci_src_table_name:    运维自定义 来源表名称\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_src_table_name;\n" +
				"ALTER TABLE 【tableName】 ADD omci_src_table_name varchar(50) NOT NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_src_table_name IS '运维自定义 来源表名称';\n" +
				"\n" +
				"--omci_del_time:\t运维自定义 删除时间\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_del_time;\n" +
				"ALTER TABLE 【tableName】 ADD omci_del_time timestamp NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_del_time IS '运维自定义 删除时间';\n" +
				"\n" +
				"--omci_del_state:\t运维自定义 删除状态(0:正常,1:已删除)     必填\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_del_state;\n" +
				"ALTER TABLE 【tableName】 ADD omci_del_state int2 NOT NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_del_state IS '运维自定义 删除状态(0:正常,1:已删除)';\n" +
				"\n" +
				"--omci_level:\t运维自定义 更新优先级(高级覆盖低级)\n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_level;\n" +
				"ALTER TABLE 【tableName】 ADD omci_level int2 NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_level IS '运维自定义 更新优先级(高级覆盖低级)';\n" +
				"\n" +
				"--omci_id:\t运维自定义 主键 \n" +
				"-- ALTER TABLE 【tableName】 DROP COLUMN omci_id;\n" +
				"ALTER TABLE 【tableName】 ADD omci_id varchar(50) NOT NULL;\n" +
				"COMMENT ON COLUMN 【tableName】.omci_id IS '运维自定义 主键';" +
				"---------------------------------------"+
				"\n\n\n\n";

        StringBuilder content = new StringBuilder();
        for (String tbTemp : subject_data_graph_list) {
            String new_sql = ori_sql.replace("【tableName】", tbTemp);
            content.append(new_sql);
        }
        writeFile(file, content.toString());
    }

    // 把内容写到文件，覆盖原有文件
    public static void writeFile(File file, String fileContent) {
        try {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);

            writer.write(fileContent);
            writer.close();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
