module.exports = {
    paths: {
        //public: 'src/main/resources/static',
        public: 'target/classes/static',
        watched: ['src/main/resources/static/js', 'src/main/resources/static/vendor'],
    },
    modules: {
        definition: false,
        wrapper: false
    },
    npm: {
        enabled: false
    },
    files: {
        javascripts: {
            joinTo: {
                'js/app.js': /^src\/main\/resources\/static\/js/,
                'js/vendor.js': [
                                 'src/main/resources/static/vendor/jquery/dist/jquery.min.js',
                                 'src/main/resources/static/vendor/bootstrap/dist/js/bootstrap.min.js',
                                 'src/main/resources/static/vendor/angular/angular.min.js',
                                 'src/main/resources/static/vendor/angular-route/angular-route.min.js',
                                 'src/main/resources/static/vendor/angular-sanitize/angular-sanitize.min.js',
                                 'src/main/resources/static/vendor/angular-resource/angular-resource.min.js',
                                 'src/main/resources/static/vendor/angular-translate/angular-translate.min.js',
                                 'src/main/resources/static/vendor/angular-translate-loader-url/angular-translate-loader-url.min.js',
                                 'src/main/resources/static/vendor/angular-translate-loader-static-files/angular-translate-loader-static-files.min.js',
                                 'src/main/resources/static/vendor/angular-spring-data-rest/dist/angular-spring-data-rest.js',
                                 'src/main/resources/static/vendor/angular-bootstrap/ui-bootstrap.min.js'
                ]
            },
            order: {
                before: [
                    'src/main/resources/static/js/indigo.config.js',
                    'src/main/resources/static/js/indigo.routes.js',
                    //'src/main/resources/static/js/components/header/header.service.js',

                    'src/main/resources/static/vendor/jquery/dist/jquery.min.js',
                    'src/main/resources/static/vendor/bootstrap/dist/js/bootstrap.min.js',
                    'src/main/resources/static/vendor/angular/angular.min.js',
                    'src/main/resources/static/vendor/angular-route/angular-route.min.js',
                    'src/main/resources//usr/bin/brunchstatic/vendor/angular-sanitize/angular-sanitize.min.js',
                    'src/main/resources/static/vendor/angular-resource/angular-resource.min.js',
                    'src/main/resources/static/vendor/angular-translate/angular-translate.min.js',
                    'src/main/resources/static/vendor/angular-translate-loader-url/angular-translate-loader-url.min.js',
                    'src/main/resources/static/vendor/angular-translate-loader-static-files/angular-translate-loader-static-files.min.js',
                    'src/main/resources/static/vendor/angular-spring-data-rest/dist/angular-spring-data-rest.js',
                    'src/main/resources/static/vendor/angular-bootstrap/ui-bootstrap.min.js'
                ]
            }
        },
        stylesheets: {joinTo: {'css/vendor.css': /^src\/main\/resources\/static\/vendor/}}
    },
    conventions: {
        ignored: ['src/main/resources/static/js/app.js', 'src/main/resources/static/js/vendor.js', 'src/main/resources/static/css/vendor.css']
    },
    plugins: {
        //babel: {presets: ['es2015']},
        //ng_annotate: {
        //    pattern: /^src\/main\/resources\/static\/js/
        //}
    }
};
