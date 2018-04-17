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
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'}
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
                'caption': 'Computing SLA',
                entries: [
                    {name: 'metrics.teamId',  caption: 'organization'},
                ],
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('sla', '/sla', '/sla/:id', ['SignedSlaComp'], slaSpec);
    $engineProvider.document('adminSla', '/asla', '/asla/:id', ['SignedSlaComp'], slaSpec);

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
                {name: 'siteName', caption: 'site'},
                {name: 'createdAt', type: 'date', caption: 'created'},
                {name: 'states.mainState', caption: 'status'},
                {name: 'metrics.startComp', caption: 'Start', type: 'date'},
                {name: 'metrics.endComp', caption: 'End', type: 'date'}
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
                'caption': 'Storage SLA',
                entries: [
                    {name: 'metrics.teamId',  caption: 'organization'},
                ],
            },
            showValidationButton: true,
            summary: true
        }
    };
    $engineProvider.document('slaS', '/slaS', '/slaS/:id', ['SignedSlaStorage'], slaSSpec);
    $engineProvider.document('adminSlaS', '/aslaS', '/aslaS/:id', ['SignedSlaComp'], slaSSpec);


    $engineProvider.dashboard({url: '/dashboard', label: 'SLAs', activetab: 'dashboard'},
        [
            {
                queryId: 'SignedSlaComp',
                label: 'Binding Computing SLAs',
                documentModelId: 'sla',
                showCreateButton: false,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'SignedSlaStorage',
                label: 'Binding Storage SLAs',
                documentModelId: 'slaS',
                showCreateButton: false,
                controller: 'indigoDocumentListCtrl',
                contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html'
            },
            {
                queryId: 'workingSla',
                label: 'Computing SLA Negotiations',
                documentModelId: 'sla',
                showCreateButton: true,
            },
            {
                queryId: 'workingSlaS',
                label: 'Storage SLA Negotiations',
                documentModelId: 'slaS',
                showCreateButton: true,
            },
            {
                queryId: 'rejectedSla',
                label: 'Rejected SLAs',
                documentModelId: 'sla',
                showCreateButton: false,
            }
        ]);

    if (SESSION.roles.indexOf("ROLE_PROVIDER") != -1 || SESSION.roles.indexOf("ROLE_ADMIN") != -1) {
        $engineProvider.dashboard({url: '/provider', label: 'SLAs', activetab: 'provider'},
            [
                {
                    queryId: 'AllComputingSlasProvider',
                    label: 'Binding Computing SLAs',
                    documentModelId: 'adminSla',
                    contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html',
                    showCreateButton: false

                },
                {
                    queryId: 'AllStorageSlasProvider',
                    label: 'Binding Storage SLAs',
                    documentModelId: 'adminSlaS',
                    contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html',
                    showCreateButton: false

                },

                {
                    queryId: 'inProgressComputingSlasProvider',
                    label: 'Computing SLA Negotiations',
                    documentModelId: 'adminSla',
                    showCreateButton: false

                },
                {
                    queryId: 'inProgressStorageSlasProvider',
                    label: 'Storage SLA Negotiations',
                    documentModelId: 'adminSlaS',
                    showCreateButton: false
                },

            ]);
    }

    if (SESSION.roles.indexOf("ROLE_ADMIN") != -1) {
        $engineProvider.dashboard({url: '/admin', label: 'Admin', activetab: 'admin'},
            [
                {
                    queryId: 'AllComputingSlasAdmin',
                    label: 'Computing SLAs',
                    documentModelId: 'adminSla',
                    contentTemplateUrl: '/js/engine/indigoDocumentList.tpl.html',
                    showCreateButton: false
                },
                {
                    queryId: 'AllStorageSlasAdmin',
                    label: 'Storage SLAs',
                    documentModelId: 'adminSlaS',
                    showCreateButton: false
                },

            ]);
    }
});