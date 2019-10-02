<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-annonce" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <table>
                <thead>
                <tr>
                    <th class="sortable"><a href="/user/index?sort=title&amp;max=10&amp;order=asc">Titre</a></th>
                    <th class="sortable"><a href="/user/index?sort=description&amp;max=10&amp;order=asc">Description</a></th>
                    <th class="sortable"><a href="/user/index?sort=validTill&amp;max=10&amp;order=asc">Date de validité</a></th>
                    <th class="sortable"><a href="/user/index?sort=illustrations&amp;max=10&amp;order=asc">Illustrations</a></th>
                    <th class="sortable"><a href="/user/index?sort=state&amp;max=10&amp;order=asc">État</a></th>
                    <th class="sortable"><a href="/user/index?sort=author&amp;max=10&amp;order=asc">Auteur</a></th>
                </tr>
                </thead>
                <g:each in="${annonceList}" var="annonce">
                    <tr>
                        <td><g:link controller="annonce" action="show" id="${annonce.id}">${annonce.title}</g:link></td>
                        <td>${annonce.description}</td>
                        <td>${annonce.validTill}</td>
                        <td>
                            <ul>
                                <g:each in="${annonce.illustration}" var="illustration">
                                    <li><g:link controller="illustration" action="show" id="${illustration.id}">${illustration.filename}</g:link></li>
                                </g:each>
                            </ul>
                        </td>
                        <td>
                            <g:if test="${annonce.state == true}">
                                Valide
                            </g:if>
                            <g:else>
                                Expirée
                            </g:else>
                        </td>
                        <td><g:link controller="user" action="show" id="${annonce.author}">${annonce.author}</g:link></td>
                    </tr>
                </g:each>
            </table>

            <div class="pagination">
                <g:paginate total="${annonceCount ?: 0}" />
            </div>
        </div>
    </body>
</html>