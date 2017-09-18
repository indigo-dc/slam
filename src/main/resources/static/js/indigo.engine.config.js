var app = angular.module('indigo');

app.config(function ($engineProvider, SESSION) {
    /**
     * SPECIFYING SLA
     */
    var slaSpec = {
        documentJSON: {
            "states": {
                "documentType": "sla",
                "serviceType": "computing"
            },
            "metrics": {}
        },
        name: 'Computing SLA',
        list: {
            columns: [
                {name: '@index', type: 'link', caption: 'ID'},
                {name: 'name', caption: 'Name'},
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'},
                // {name: 'discipline', caption: 'disciplne'},
                // {name: 'proposalEvaluation', condition: "states.documentState == 'evaluated"}
            ],
            caption: 'SLAs'
        },
        document: {
            steps: [
                {
                    name: 'GENERAL',
                    categories: 'mcComputing'
                }
            ],
            details: {
                'caption': 'Computing SLA'
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('sla', '/sla', '/sla/:id', ['SignedSlaComp'], slaSpec);

    var slaSSpec = {
        documentJSON: {
            "states": {
                "documentType": "sla",
                "serviceType": "storage"
            },
            "metrics": {}
        },
        name: 'Storage SLA',
        list: {
            columns: [
                {name: '@index', type: 'link', caption: 'ID'},
                {name: 'name', caption: 'Name'},
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'},
                // {name: 'discipline', caption: 'disciplne'},
                // {name: 'proposalEvaluation', condition: "states.documentState == 'evaluated"}
            ],
            caption: 'Storage SLAs'
        },
        document: {
            steps: [
                {
                    name: 'GENERAL',
                    categories: 'mcStorage'
                }
            ],
            details: {
                'caption': 'Storage SLA'
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('slaS', '/slaS', '/slaS/:id', ['SignedSlaComp'], slaSSpec);


    $engineProvider.dashboard({url: '/dashboard', label: 'SLAs', activetab: 'dashboard'},
        [
            {
                queryId: 'SignedSlaComp',
                label: 'Binding Computing SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            },
            {
                queryId: 'SignedSlaStorage',
                label: 'Binding Storage SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            },
            {
                queryId: 'workingSla',
                label: 'Computing SLA Drafts',
                documentModelId: 'sla',
                showCreateButton: true,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'workingSlaS',
                label: 'Storage SLA Drafts',
                documentModelId: 'slaS',
                showCreateButton: true,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'rejectedSla',
                label: 'Rejected SLAs',
                documentModelId: 'sla',
                showCreateButton: false
            }
        ]);

    if (SESSION.roles.indexOf("ROLE_PROVIDER") != -1) {
        $engineProvider.dashboard({url: '/provider', label: 'SLAs', activetab: 'provider'},
            [
                {
                    queryId: 'AllSlasProvider',
                    label: 'Binding SLAs',
                    documentModelId: 'sla',
                    showCreateButton: false
                },
                {
                    queryId: 'inProgressSlasProvider',
                    label: 'SLA in Negotiations',
                    documentModelId: 'sla',
                    showCreateButton: false
                },
            ]);
    }
});