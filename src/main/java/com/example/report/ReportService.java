package com.example.report;

import java.util.ArrayList;
import java.util.List;

public class ReportService {

    private final SvcContactsRepository svcContactsRepository;

    public ReportService(SvcContactsRepository svcContactsRepository) {
        this.svcContactsRepository = svcContactsRepository;
    }

    private List<ReportGroup> initProductGroupList(Long reportId, Long linkId, String groupType, String billingNo, String[] groupDisplayName) {
        List<ReportGroup> groupList = new ArrayList<>();

        try {
            List<SvcContacts> list = svcContactsRepository.findByBillingNoContainingAndIsDeleted(billingNo, "0");
            if (list != null && list.size() > 0) {
                SvcContacts svcContacts = list.get(0);
                for (int i = 0; i < groupDisplayName.length; i++) {
                    String groupName = groupDisplayName[i];
                    ReportGroup reportGroup = new ReportGroup();
                    reportGroup.setReportId(reportId);
                    reportGroup.setLinkId(linkId);
                    reportGroup.setGroupId(-1L);
                    if ("运维A角".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getLocalOpsA());
                        reportGroup.setUserName(svcContacts.getLocalOpsA());
                        reportGroup.setMobile(svcContacts.getLocalOpsAPhone());
                    } else if ("运维B角".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getLocalOpsB());
                        reportGroup.setUserName(svcContacts.getLocalOpsB());
                        reportGroup.setMobile(svcContacts.getLocalOpsBPhone());
                    } else if ("省公司责任人".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getProvPerson());
                        reportGroup.setUserName(svcContacts.getProvPerson());
                        reportGroup.setMobile(svcContacts.getProvPersonPhone());
                    } else if ("地市客响班组长".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getLocalLeader());
                        reportGroup.setUserName(svcContacts.getLocalLeader());
                        reportGroup.setMobile(svcContacts.getLocalLeaderPhone());
                    } else if ("地市责任经理".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getLocalManager());
                        reportGroup.setUserName(svcContacts.getLocalManager());
                        reportGroup.setMobile(svcContacts.getLocalManagerPhone());
                    } else if ("省公司责任主管".equals(groupName)) {
                        reportGroup.setUserId(svcContacts.getProvSupervisor());
                        reportGroup.setUserName(svcContacts.getProvSupervisor());
                        reportGroup.setMobile(svcContacts.getProvSupervisorPhone());
                    }
                    reportGroup.setGroupType(groupType);
                    reportGroup.setGroupCode(groupDisplayName[i]);
                    if ("voice".equals(groupType)) {
                        reportGroup.setUserLevel(1L);
                    }
                    groupList.add(reportGroup);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return groupList;
    }
}
