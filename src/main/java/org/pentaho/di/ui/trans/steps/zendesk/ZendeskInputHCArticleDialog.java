/*! ******************************************************************************
*
* Pentaho Data Integration
*
* Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
*
*******************************************************************************
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
******************************************************************************/

package org.pentaho.di.ui.trans.steps.zendesk;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Props;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.steps.zendesk.ZendeskInputHCArticleMeta;
import org.pentaho.di.ui.core.dialog.ErrorDialog;
import org.pentaho.di.ui.core.widget.LabelTextVar;
import org.pentaho.di.ui.core.widget.PasswordTextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class ZendeskInputHCArticleDialog extends BaseStepDialog implements StepDialogInterface {

 private static Class<?> PKG = ZendeskInputHCArticleMeta.class; // for i18n purposes, needed by Translator2!!
 private ZendeskInputHCArticleMeta input;
 private boolean isReceivingInput;

 private CTabFolder wTabFolder;
 private CTabItem wGeneralTab, wArticleTab, wTranslationTab;
 private Composite wGeneralComp, wArticleComp, wTranslationComp;

 private LabelTextVar wSubDomain, wUsername;
 private Label wlPassword, wlToken;
 private PasswordTextVar wPassword;
 private Button wToken;

 private CCombo wIncomingFieldname;
 private LabelTextVar wArticleIdFieldname;
 private LabelTextVar wArticleUrlFieldname;
 private LabelTextVar wArticleTitleFieldname;
 private LabelTextVar wArticleBodyFieldname;
 private LabelTextVar wLocaleFieldname;
 private LabelTextVar wSourceLocaleFieldname;
 private LabelTextVar wAuthorIdFieldname;
 private LabelTextVar wCommentsDisabledFieldname;
 private LabelTextVar wOutdatedFieldname;
 private LabelTextVar wLabelsFieldname;
 private LabelTextVar wDraftFieldname;
 private LabelTextVar wPromotedFieldname;
 private LabelTextVar wPositionFieldname;
 private LabelTextVar wVoteSumFieldname;
 private LabelTextVar wVoteCountFieldname;
 private LabelTextVar wSectionIdFieldname;
 private LabelTextVar wCreatedAtFieldname;
 private LabelTextVar wUpdatedAtFieldname;

 private LabelTextVar wTranslationIdFieldname;
 private LabelTextVar wTranslationUrlFieldname;
 private LabelTextVar wTranslationHtmlUrlFieldname;
 private LabelTextVar wTranslationSourceIdFieldname;
 private LabelTextVar wTranslationSourceTypeFieldname;
 private LabelTextVar wTranslationLocaleFieldname;
 private LabelTextVar wTranslationTitleFieldname;
 private LabelTextVar wTranslationBodyFieldname;
 private LabelTextVar wTranslationOutdatedFieldname;
 private LabelTextVar wTranslationDraftFieldname;
 private LabelTextVar wTranslationCreatedAtFieldname;
 private LabelTextVar wTranslationUpdatedAtFieldname;
 private LabelTextVar wTranslationUpdatedByIdFieldname;
 private LabelTextVar wTranslationCreatedByIdFieldname;

 public ZendeskInputHCArticleDialog( Shell parent, Object in, TransMeta tr, String sname ) {
   super( parent, (BaseStepMeta) in, tr, sname );
   input = (ZendeskInputHCArticleMeta) in;
 }

 public String open() {
   Shell parent = getParent();
   Display display = parent.getDisplay();

   shell = new Shell( parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX );
   props.setLook( shell );
   setShellImage( shell, input );

   ModifyListener lsMod = new ModifyListener() {
     public void modifyText( ModifyEvent e ) {
       input.setChanged();
     }
   };
   changed = input.hasChanged();

   FormLayout formLayout = new FormLayout();
   formLayout.marginWidth = Const.FORM_MARGIN;
   formLayout.marginHeight = Const.FORM_MARGIN;

   shell.setLayout( formLayout );
   shell.setText( BaseMessages.getString( PKG, "ZendeskInputHCArticles.Shell.Title" ) );

   int middle = props.getMiddlePct();
   int margin = Const.MARGIN;

   // Stepname line
   wlStepname = new Label( shell, SWT.RIGHT );
   wlStepname.setText( BaseMessages.getString( PKG, "ZendeskInputDialog.Stepname.Label" ) );
   props.setLook( wlStepname );
   fdlStepname = new FormData();
   fdlStepname.left = new FormAttachment( 0, 0 );
   fdlStepname.right = new FormAttachment( middle, -margin );
   fdlStepname.top = new FormAttachment( 0, margin );
   wlStepname.setLayoutData( fdlStepname );
   wStepname = new Text( shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   wStepname.setText( stepname );
   props.setLook( wStepname );
   wStepname.addModifyListener( lsMod );
   fdStepname = new FormData();
   fdStepname.left = new FormAttachment( middle, 0 );
   fdStepname.top = new FormAttachment( 0, margin );
   fdStepname.right = new FormAttachment( 100, 0 );
   wStepname.setLayoutData( fdStepname );

   // The Tab Folders
   wTabFolder = new CTabFolder( shell, SWT.BORDER );
   props.setLook(  wTabFolder, Props.WIDGET_STYLE_TAB );

   // ///////////////////////
   // START OF GENERAL TAB //
   // ///////////////////////

   wGeneralTab = new CTabItem( wTabFolder, SWT.NONE );
   wGeneralTab.setText( BaseMessages.getString( PKG, "ZendeskInputDialog.GeneralTab.TabItem" ) );

   wGeneralComp = new Composite( wTabFolder, SWT.NONE );
   props.setLook( wGeneralComp );

   FormLayout generalLayout = new FormLayout();
   generalLayout.marginWidth = margin;
   generalLayout.marginHeight = margin;
   wGeneralComp.setLayout( generalLayout );

   // Subdomain
   wSubDomain = new LabelTextVar( transMeta, wGeneralComp,
     BaseMessages.getString( PKG, "ZendeskInputDialog.SubDomain.Label" ),
     BaseMessages.getString( PKG, "ZendeskInputDialog.SubDomain.Tooltip" ) );
   props.setLook( wSubDomain );
   wSubDomain.addModifyListener( lsMod );
   FormData fdSubDomain = new FormData();
   fdSubDomain.left = new FormAttachment( 0, -margin );
   fdSubDomain.top = new FormAttachment( wStepname, 2 * margin );
   fdSubDomain.right = new FormAttachment( 100, -margin );
   wSubDomain.setLayoutData( fdSubDomain );

   // Username
   wUsername =
     new LabelTextVar(
       transMeta, wGeneralComp, BaseMessages.getString( PKG, "ZendeskInputDialog.Username.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputDialog.Username.Tooltip" ) );
   props.setLook( wUsername );
   wUsername.addModifyListener( lsMod );
   FormData fdUsername = new FormData();
   fdUsername.left = new FormAttachment( 0, -margin );
   fdUsername.top = new FormAttachment( wSubDomain, 2 * margin );
   fdUsername.right = new FormAttachment( 100, -margin );
   wUsername.setLayoutData( fdUsername );

   // Password
   wlPassword = new Label( wGeneralComp, SWT.RIGHT );
   wlPassword.setText( BaseMessages.getString( PKG, "ZendeskInputDialog.Password.Label" ) );
   props.setLook( wlPassword );
   FormData fdlPassword = new FormData();
   fdlPassword.left = new FormAttachment( 0, 0 );
   fdlPassword.top = new FormAttachment( wUsername, 2 * margin );
   fdlPassword.right = new FormAttachment( middle, -margin );
   wlPassword.setLayoutData( fdlPassword );
   
   wPassword = new PasswordTextVar( transMeta, wGeneralComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER,
     BaseMessages.getString( PKG, "ZendeskInputDialog.Password.Tooltip" ) );
   props.setLook( wPassword );
   wPassword.addModifyListener( lsMod );
   FormData fdPassword = new FormData();
   fdPassword.left = new FormAttachment( middle, 0 );
   fdPassword.top = new FormAttachment( wUsername, margin );
   fdPassword.right = new FormAttachment( 100, -margin );
   wPassword.setLayoutData( fdPassword );

   // Token
   wlToken = new Label( wGeneralComp, SWT.RIGHT );
   wlToken.setText( BaseMessages.getString( PKG, "ZendeskInputDialog.Token.Label" ) );
   props.setLook( wlToken );
   FormData fdlToken = new FormData();
   fdlToken.left = new FormAttachment( 0, 0 );
   fdlToken.top = new FormAttachment( wlPassword, 2 * margin );
   fdlToken.right = new FormAttachment( middle, -margin );
   wlToken.setLayoutData( fdlToken );

   wToken = new Button( wGeneralComp, SWT.CHECK );
   props.setLook( wToken );
   wToken.setToolTipText( BaseMessages.getString( PKG, "ZendeskInputDialog.Token.Tooltip" ) );
   FormData fdToken = new FormData();
   fdToken.left = new FormAttachment( middle, 0 );
   fdToken.top = new FormAttachment( wPassword, margin );
   fdToken.right = new FormAttachment( 100, -margin );
   wToken.setLayoutData( fdToken );
   wToken.addSelectionListener( new SelectionAdapter() {
     public void widgetSelected(SelectionEvent e) {
       input.setChanged();
     }
   } );

   Label wlIncomingFieldname = new Label( wGeneralComp, SWT.RIGHT );
   wlIncomingFieldname.setText( BaseMessages.getString( PKG, "ZendeskInputOrganizationsDialog.IncomingFieldname.Label" ) );
   wlIncomingFieldname.setToolTipText( BaseMessages.getString( PKG, "ZendeskInputOrganizationsDialog.IncomingFieldname.Tooltip" ) );
   props.setLook( wlIncomingFieldname );
   FormData fdlIncomingFieldname = new FormData();
   fdlIncomingFieldname.left = new FormAttachment( 0, 0 );
   fdlIncomingFieldname.top = new FormAttachment( wlToken, 2 * margin );
   fdlIncomingFieldname.right = new FormAttachment( middle, -margin );
   wlIncomingFieldname.setLayoutData( fdlIncomingFieldname );

   wIncomingFieldname = new CCombo( wGeneralComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER );
   props.setLook( wIncomingFieldname );
   wIncomingFieldname.addModifyListener( lsMod );
   FormData fdIncomingFieldname = new FormData();
   fdIncomingFieldname.left = new FormAttachment( middle, 0 );
   fdIncomingFieldname.top = new FormAttachment( wToken, 2 * margin );
   fdIncomingFieldname.right = new FormAttachment( 100, -margin );
   wIncomingFieldname.setLayoutData( fdIncomingFieldname );

   wlIncomingFieldname.setVisible( false );
   wIncomingFieldname.setVisible( false );
   isReceivingInput = transMeta.findNrPrevSteps( stepMeta ) > 0;
   if ( isReceivingInput ) {
     wlIncomingFieldname.setVisible( true );
     wIncomingFieldname.setVisible( true );
     RowMetaInterface previousFields;
     try {
       previousFields = transMeta.getPrevStepFields( stepMeta );
     } catch ( KettleStepException e ) {
       new ErrorDialog( shell,
         BaseMessages.getString( PKG, "ZendeskInputDialog.Error.UnableToGetInputFields.Title" ),
         BaseMessages.getString( PKG, "ZendeskInputDialog.Error.UnableToGetInputFields.Message" ), e );
       previousFields = new RowMeta();
     }

     String[] fieldnames = previousFields.getFieldNames();
     if ( fieldnames.length > 0 && !Arrays.asList( fieldnames ).contains( null )) {
       wIncomingFieldname.setItems( previousFields.getFieldNames() );
     }
   }

   FormData fdGeneralComp = new FormData();
   fdGeneralComp.left = new FormAttachment( 0, 0 );
   fdGeneralComp.top = new FormAttachment( 0, 0 );
   fdGeneralComp.right = new FormAttachment( 100, 0 );
   fdGeneralComp.bottom = new FormAttachment( 100, 0 );
   wGeneralComp.setLayoutData( fdGeneralComp );

   wGeneralComp.layout();
   wGeneralTab.setControl( wGeneralComp );

   // /////////////////////
   // END OF GENERAL TAB //
   // /////////////////////

   // ///////////////////////
   // START OF ARTICLE TAB //
   // ///////////////////////

   wArticleTab = new CTabItem( wTabFolder, SWT.NONE );
   wArticleTab.setText( BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleTab.TabItem" ) );

   wArticleComp = new Composite( wTabFolder, SWT.NONE );
   props.setLook( wArticleComp );

   FormLayout articleLayout = new FormLayout();
   articleLayout.marginWidth = margin;
   articleLayout.marginHeight = margin;
   wArticleComp.setLayout( articleLayout );

   // articleIdFieldname
   wArticleIdFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleIdFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleIdFieldname.Tooltip" ) );
   props.setLook( wArticleIdFieldname );
   wArticleIdFieldname.addModifyListener( lsMod );
   FormData fdArticleIdFieldname = new FormData();
   fdArticleIdFieldname.left = new FormAttachment( 0, -margin );
   fdArticleIdFieldname.top = new FormAttachment( wArticleComp, 2 * margin );
   fdArticleIdFieldname.right = new FormAttachment( 100, -margin );
   wArticleIdFieldname.setLayoutData( fdArticleIdFieldname );

   // articleUrlFieldname
   wArticleUrlFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleURLFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleURLFieldname.Tooltip" ) );
   props.setLook( wArticleUrlFieldname );
   wArticleUrlFieldname.addModifyListener( lsMod );
   FormData fdArticleUrlFieldname = new FormData();
   fdArticleUrlFieldname.left = new FormAttachment( 0, -margin );
   fdArticleUrlFieldname.top = new FormAttachment( wArticleIdFieldname, 2 * margin );
   fdArticleUrlFieldname.right = new FormAttachment( 100, -margin );
   wArticleUrlFieldname.setLayoutData( fdArticleUrlFieldname );

   // articleTitleFieldname
   wArticleTitleFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleTitleFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleTitleFieldname.Tooltip" ) );
   props.setLook( wArticleTitleFieldname );
   wArticleTitleFieldname.addModifyListener( lsMod );
   FormData fdArticleTitleFieldname = new FormData();
   fdArticleTitleFieldname.left = new FormAttachment( 0, -margin );
   fdArticleTitleFieldname.top = new FormAttachment( wArticleUrlFieldname, 2 * margin );
   fdArticleTitleFieldname.right = new FormAttachment( 100, -margin );
   wArticleTitleFieldname.setLayoutData( fdArticleTitleFieldname );

   // articleBodyFieldname
   wArticleBodyFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleBodyFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.ArticleBodyFieldname.Tooltip" ) );
   props.setLook( wArticleBodyFieldname );
   wArticleBodyFieldname.addModifyListener( lsMod );
   FormData fdArticleBodyFieldname = new FormData();
   fdArticleBodyFieldname.left = new FormAttachment( 0, -margin );
   fdArticleBodyFieldname.top = new FormAttachment( wArticleTitleFieldname, 2 * margin );
   fdArticleBodyFieldname.right = new FormAttachment( 100, -margin );
   wArticleBodyFieldname.setLayoutData( fdArticleBodyFieldname );

   // localeFieldname
   wLocaleFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.LocaleFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.LocaleFieldname.Tooltip" ) );
   props.setLook( wLocaleFieldname );
   wLocaleFieldname.addModifyListener( lsMod );
   FormData fdLocaleFieldname = new FormData();
   fdLocaleFieldname.left = new FormAttachment( 0, -margin );
   fdLocaleFieldname.top = new FormAttachment( wArticleBodyFieldname, 2 * margin );
   fdLocaleFieldname.right = new FormAttachment( 100, -margin );
   wLocaleFieldname.setLayoutData( fdLocaleFieldname );

   // sourceLocaleFieldname
   wSourceLocaleFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.SourceLocaleFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.SourceLocaleFieldname.Tooltip" ) );
   props.setLook( wSourceLocaleFieldname );
   wSourceLocaleFieldname.addModifyListener( lsMod );
   FormData fdSourceLocaleFieldname = new FormData();
   fdSourceLocaleFieldname.left = new FormAttachment( 0, -margin );
   fdSourceLocaleFieldname.top = new FormAttachment( wLocaleFieldname, 2 * margin );
   fdSourceLocaleFieldname.right = new FormAttachment( 100, -margin );
   wSourceLocaleFieldname.setLayoutData( fdSourceLocaleFieldname );

   // authorIdFieldname
   wAuthorIdFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.AuthorIDFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.AuthorIDFieldname.Tooltip" ) );
   props.setLook( wAuthorIdFieldname );
   wAuthorIdFieldname.addModifyListener( lsMod );
   FormData fdAuthorIdFieldname = new FormData();
   fdAuthorIdFieldname.left = new FormAttachment( 0, -margin );
   fdAuthorIdFieldname.top = new FormAttachment( wSourceLocaleFieldname, 2 * margin );
   fdAuthorIdFieldname.right = new FormAttachment( 100, -margin );
   wAuthorIdFieldname.setLayoutData( fdAuthorIdFieldname );

   // commentsDisabledFieldname
   wCommentsDisabledFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.CommentsDisabledFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.CommentsDisabledFieldname.Tooltip" ) );
   props.setLook( wCommentsDisabledFieldname );
   wCommentsDisabledFieldname.addModifyListener( lsMod );
   FormData fdCommentsDisabledFieldname = new FormData();
   fdCommentsDisabledFieldname.left = new FormAttachment( 0, -margin );
   fdCommentsDisabledFieldname.top = new FormAttachment( wAuthorIdFieldname, 2 * margin );
   fdCommentsDisabledFieldname.right = new FormAttachment( 100, -margin );
   wCommentsDisabledFieldname.setLayoutData( fdCommentsDisabledFieldname );

   // outdatedFieldname
   wOutdatedFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.OutdatedFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.OutdatedFieldname.Tooltip" ) );
   props.setLook( wOutdatedFieldname );
   wOutdatedFieldname.addModifyListener( lsMod );
   FormData fdOutdatedFieldname = new FormData();
   fdOutdatedFieldname.left = new FormAttachment( 0, -margin );
   fdOutdatedFieldname.top = new FormAttachment( wCommentsDisabledFieldname, 2 * margin );
   fdOutdatedFieldname.right = new FormAttachment( 100, -margin );
   wOutdatedFieldname.setLayoutData( fdOutdatedFieldname );

   // labelsFieldname
   wLabelsFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.LabelsFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.LabelsFieldname.Tooltip" ) );
   props.setLook( wLabelsFieldname );
   wLabelsFieldname.addModifyListener( lsMod );
   FormData fdLabelsFieldname = new FormData();
   fdLabelsFieldname.left = new FormAttachment( 0, -margin );
   fdLabelsFieldname.top = new FormAttachment( wOutdatedFieldname, 2 * margin );
   fdLabelsFieldname.right = new FormAttachment( 100, -margin );
   wLabelsFieldname.setLayoutData( fdLabelsFieldname );

   // draftFieldname
   wDraftFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.DraftFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.DraftFieldname.Tooltip" ) );
   props.setLook( wDraftFieldname );
   wDraftFieldname.addModifyListener( lsMod );
   FormData fdDraftFieldname = new FormData();
   fdDraftFieldname.left = new FormAttachment( 0, -margin );
   fdDraftFieldname.top = new FormAttachment( wLabelsFieldname, 2 * margin );
   fdDraftFieldname.right = new FormAttachment( 100, -margin );
   wDraftFieldname.setLayoutData( fdDraftFieldname );

   // promotedFieldname
   wPromotedFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.PromotedFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.PromotedFieldname.Tooltip" ) );
   props.setLook( wPromotedFieldname );
   wPromotedFieldname.addModifyListener( lsMod );
   FormData fdPromotedFieldname = new FormData();
   fdPromotedFieldname.left = new FormAttachment( 0, -margin );
   fdPromotedFieldname.top = new FormAttachment( wDraftFieldname, 2 * margin );
   fdPromotedFieldname.right = new FormAttachment( 100, -margin );
   wPromotedFieldname.setLayoutData( fdPromotedFieldname );

   // positionFieldname
   wPositionFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.PositionFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.PositionFieldname.Tooltip" ) );
   props.setLook( wPositionFieldname );
   wPositionFieldname.addModifyListener( lsMod );
   FormData fdPositionFieldname = new FormData();
   fdPositionFieldname.left = new FormAttachment( 0, -margin );
   fdPositionFieldname.top = new FormAttachment( wPromotedFieldname, 2 * margin );
   fdPositionFieldname.right = new FormAttachment( 100, -margin );
   wPositionFieldname.setLayoutData( fdPositionFieldname );

   // voteSumFieldname
   wVoteSumFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.VoteSumFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.VoteSumFieldname.Tooltip" ) );
   props.setLook( wVoteSumFieldname );
   wVoteSumFieldname.addModifyListener( lsMod );
   FormData fdVoteSumFieldname = new FormData();
   fdVoteSumFieldname.left = new FormAttachment( 0, -margin );
   fdVoteSumFieldname.top = new FormAttachment( wPositionFieldname, 2 * margin );
   fdVoteSumFieldname.right = new FormAttachment( 100, -margin );
   wVoteSumFieldname.setLayoutData( fdVoteSumFieldname );

   // voteCountFieldname
   wVoteCountFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.VoteCountFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.VoteCountFieldname.Tooltip" ) );
   props.setLook( wVoteCountFieldname );
   wVoteCountFieldname.addModifyListener( lsMod );
   FormData fdVoteCountFieldname = new FormData();
   fdVoteCountFieldname.left = new FormAttachment( 0, -margin );
   fdVoteCountFieldname.top = new FormAttachment( wVoteSumFieldname, 2 * margin );
   fdVoteCountFieldname.right = new FormAttachment( 100, -margin );
   wVoteCountFieldname.setLayoutData( fdVoteCountFieldname );

   // sectionIdFieldname
   wSectionIdFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.SectionIDFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.SectionIDFieldname.Tooltip" ) );
   props.setLook( wSectionIdFieldname );
   wSectionIdFieldname.addModifyListener( lsMod );
   FormData fdSectionIdFieldname = new FormData();
   fdSectionIdFieldname.left = new FormAttachment( 0, -margin );
   fdSectionIdFieldname.top = new FormAttachment( wVoteCountFieldname, 2 * margin );
   fdSectionIdFieldname.right = new FormAttachment( 100, -margin );
   wSectionIdFieldname.setLayoutData( fdSectionIdFieldname );

   // createdAtFieldname
   wCreatedAtFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.CreatedAtFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.CreatedAtFieldname.Tooltip" ) );
   props.setLook( wCreatedAtFieldname );
   wCreatedAtFieldname.addModifyListener( lsMod );
   FormData fdCreatedAtFieldname = new FormData();
   fdCreatedAtFieldname.left = new FormAttachment( 0, -margin );
   fdCreatedAtFieldname.top = new FormAttachment( wSectionIdFieldname, 2 * margin );
   fdCreatedAtFieldname.right = new FormAttachment( 100, -margin );
   wCreatedAtFieldname.setLayoutData( fdCreatedAtFieldname );

   // updatedAtFieldname
   wUpdatedAtFieldname =
     new LabelTextVar(
       transMeta, wArticleComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.UpdatedAtFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.UpdatedAtFieldname.Tooltip" ) );
   props.setLook( wUpdatedAtFieldname );
   wUpdatedAtFieldname.addModifyListener( lsMod );
   FormData fdUpdatedAtFieldname = new FormData();
   fdUpdatedAtFieldname.left = new FormAttachment( 0, -margin );
   fdUpdatedAtFieldname.top = new FormAttachment( wCreatedAtFieldname, 2 * margin );
   fdUpdatedAtFieldname.right = new FormAttachment( 100, -margin );
   wUpdatedAtFieldname.setLayoutData( fdUpdatedAtFieldname );

   FormData fdArticleComp = new FormData();
   fdArticleComp.left = new FormAttachment( 0, 0 );
   fdArticleComp.top = new FormAttachment( 0, 0 );
   fdArticleComp.right = new FormAttachment( 100, 0 );
   fdArticleComp.bottom = new FormAttachment( 100, 0 );
   wArticleComp.setLayoutData( fdArticleComp );

   wArticleComp.layout();
   wArticleTab.setControl( wArticleComp );

   // /////////////////////
   // END OF ARTICLE TAB //
   // /////////////////////

   // ///////////////////////////
   // START OF TRANSLATION TAB //
   // ///////////////////////////

   wTranslationTab = new CTabItem( wTabFolder, SWT.NONE );
   wTranslationTab.setText( BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationTab.TabItem" ) );

   wTranslationComp = new Composite( wTabFolder, SWT.NONE );
   props.setLook( wTranslationComp );

   FormLayout translationLayout = new FormLayout();
   translationLayout.marginWidth = margin;
   translationLayout.marginHeight = margin;
   wTranslationComp.setLayout( translationLayout );

   // translationIdFieldname
   wTranslationIdFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationIdFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationIdFieldname.Tooltip" ) );
   props.setLook( wTranslationIdFieldname );
   wTranslationIdFieldname.addModifyListener( lsMod );
   FormData fdTranslationIdFieldname = new FormData();
   fdTranslationIdFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationIdFieldname.top = new FormAttachment( wCreatedAtFieldname, 2 * margin );
   fdTranslationIdFieldname.right = new FormAttachment( 100, -margin );
   wTranslationIdFieldname.setLayoutData( fdTranslationIdFieldname );

   // translationUrlFieldname
   wTranslationUrlFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUrlFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUrlFieldname.Tooltip" ) );
   props.setLook( wTranslationUrlFieldname );
   wTranslationUrlFieldname.addModifyListener( lsMod );
   FormData fdTranslationUrlFieldname = new FormData();
   fdTranslationUrlFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationUrlFieldname.top = new FormAttachment( wTranslationIdFieldname, 2 * margin );
   fdTranslationUrlFieldname.right = new FormAttachment( 100, -margin );
   wTranslationUrlFieldname.setLayoutData( fdTranslationUrlFieldname );

   // translationHtmlUrlFieldname
   wTranslationHtmlUrlFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationHtmlUrlFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationHtmlUrlFieldname.Tooltip" ) );
   props.setLook( wTranslationHtmlUrlFieldname );
   wTranslationHtmlUrlFieldname.addModifyListener( lsMod );
   FormData fdTranslationHtmlUrlFieldname = new FormData();
   fdTranslationHtmlUrlFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationHtmlUrlFieldname.top = new FormAttachment( wTranslationUrlFieldname, 2 * margin );
   fdTranslationHtmlUrlFieldname.right = new FormAttachment( 100, -margin );
   wTranslationHtmlUrlFieldname.setLayoutData( fdTranslationHtmlUrlFieldname );

   // translationSourceIdFieldname
   wTranslationSourceIdFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationSourceIdFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationSourceIdFieldname.Tooltip" ) );
   props.setLook( wTranslationSourceIdFieldname );
   wTranslationSourceIdFieldname.addModifyListener( lsMod );
   FormData fdTranslationSourceIdFieldname = new FormData();
   fdTranslationSourceIdFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationSourceIdFieldname.top = new FormAttachment( wTranslationHtmlUrlFieldname, 2 * margin );
   fdTranslationSourceIdFieldname.right = new FormAttachment( 100, -margin );
   wTranslationSourceIdFieldname.setLayoutData( fdTranslationSourceIdFieldname );

   // translationSourceTypeFieldname
   wTranslationSourceTypeFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationSourceTypeFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationSourceTypeFieldname.Tooltip" ) );
   props.setLook( wTranslationSourceTypeFieldname );
   wTranslationSourceTypeFieldname.addModifyListener( lsMod );
   FormData fdTranslationSourceTypeFieldname = new FormData();
   fdTranslationSourceTypeFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationSourceTypeFieldname.top = new FormAttachment( wTranslationSourceIdFieldname, 2 * margin );
   fdTranslationSourceTypeFieldname.right = new FormAttachment( 100, -margin );
   wTranslationSourceTypeFieldname.setLayoutData( fdTranslationSourceTypeFieldname );

   // translationLocaleFieldname
   wTranslationLocaleFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationLocaleFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationLocaleFieldname.Tooltip" ) );
   props.setLook( wTranslationLocaleFieldname );
   wTranslationLocaleFieldname.addModifyListener( lsMod );
   FormData fdTranslationLocaleFieldname = new FormData();
   fdTranslationLocaleFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationLocaleFieldname.top = new FormAttachment( wTranslationSourceTypeFieldname, 2 * margin );
   fdTranslationLocaleFieldname.right = new FormAttachment( 100, -margin );
   wTranslationLocaleFieldname.setLayoutData( fdTranslationLocaleFieldname );

   // translationTitleFieldname
   wTranslationTitleFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationTitleFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationTitleFieldname.Tooltip" ) );
   props.setLook( wTranslationTitleFieldname );
   wTranslationTitleFieldname.addModifyListener( lsMod );
   FormData fdTranslationTitleFieldname = new FormData();
   fdTranslationTitleFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationTitleFieldname.top = new FormAttachment( wTranslationLocaleFieldname, 2 * margin );
   fdTranslationTitleFieldname.right = new FormAttachment( 100, -margin );
   wTranslationTitleFieldname.setLayoutData( fdTranslationTitleFieldname );

   // translationBodyFieldname
   wTranslationBodyFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationBodyFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationBodyFieldname.Tooltip" ) );
   props.setLook( wTranslationBodyFieldname );
   wTranslationBodyFieldname.addModifyListener( lsMod );
   FormData fdTranslationBodyFieldname = new FormData();
   fdTranslationBodyFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationBodyFieldname.top = new FormAttachment( wTranslationTitleFieldname, 2 * margin );
   fdTranslationBodyFieldname.right = new FormAttachment( 100, -margin );
   wTranslationBodyFieldname.setLayoutData( fdTranslationBodyFieldname );

   // translationOutdatedFieldname
   wTranslationOutdatedFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationOutdatedFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationOutdatedFieldname.Tooltip" ) );
   props.setLook( wTranslationOutdatedFieldname );
   wTranslationOutdatedFieldname.addModifyListener( lsMod );
   FormData fdTranslationOutdatedFieldname = new FormData();
   fdTranslationOutdatedFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationOutdatedFieldname.top = new FormAttachment( wTranslationBodyFieldname, 2 * margin );
   fdTranslationOutdatedFieldname.right = new FormAttachment( 100, -margin );
   wTranslationOutdatedFieldname.setLayoutData( fdTranslationOutdatedFieldname );

   // translationDraftFieldname
   wTranslationDraftFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationDraftFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationDraftFieldname.Tooltip" ) );
   props.setLook( wTranslationDraftFieldname );
   wTranslationDraftFieldname.addModifyListener( lsMod );
   FormData fdTranslationDraftFieldname = new FormData();
   fdTranslationDraftFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationDraftFieldname.top = new FormAttachment( wTranslationOutdatedFieldname, 2 * margin );
   fdTranslationDraftFieldname.right = new FormAttachment( 100, -margin );
   wTranslationDraftFieldname.setLayoutData( fdTranslationDraftFieldname );

   // wTranslationCreatedAtFieldname
   wTranslationCreatedAtFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationCreatedAtFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationCreatedAtFieldname.Tooltip" ) );
   props.setLook( wTranslationCreatedAtFieldname );
   wTranslationCreatedAtFieldname.addModifyListener( lsMod );
   FormData fdTranslationCreatedAtFieldname = new FormData();
   fdTranslationCreatedAtFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationCreatedAtFieldname.top = new FormAttachment( wTranslationDraftFieldname, 2 * margin );
   fdTranslationCreatedAtFieldname.right = new FormAttachment( 100, -margin );
   wTranslationCreatedAtFieldname.setLayoutData( fdTranslationCreatedAtFieldname );

   // translationUpdatedAtFieldname
   wTranslationUpdatedAtFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUpdatedAtFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUpdatedAtFieldname.Tooltip" ) );
   props.setLook( wTranslationUpdatedAtFieldname );
   wTranslationUpdatedAtFieldname.addModifyListener( lsMod );
   FormData fdTranslationUpdatedAtFieldname = new FormData();
   fdTranslationUpdatedAtFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationUpdatedAtFieldname.top = new FormAttachment( wTranslationCreatedAtFieldname, 2 * margin );
   fdTranslationUpdatedAtFieldname.right = new FormAttachment( 100, -margin );
   wTranslationUpdatedAtFieldname.setLayoutData( fdTranslationUpdatedAtFieldname );

   // translationUpdatedByIdFieldname
   wTranslationUpdatedByIdFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUpdatedByIdFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationUpdatedByIdFieldname.Tooltip" ) );
   props.setLook( wTranslationUpdatedByIdFieldname );
   wTranslationUpdatedByIdFieldname.addModifyListener( lsMod );
   FormData fdTranslationUpdatedByIdFieldname = new FormData();
   fdTranslationUpdatedByIdFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationUpdatedByIdFieldname.top = new FormAttachment( wTranslationUpdatedAtFieldname, 2 * margin );
   fdTranslationUpdatedByIdFieldname.right = new FormAttachment( 100, -margin );
   wTranslationUpdatedByIdFieldname.setLayoutData( fdTranslationUpdatedByIdFieldname );

   // translationCreatedByIdFieldname
   wTranslationCreatedByIdFieldname =
     new LabelTextVar(
       transMeta, wTranslationComp, BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationCreatedByIdFieldname.Label" ),
       BaseMessages.getString( PKG, "ZendeskInputHCArticlesDialog.TranslationCreatedByIdFieldname.Tooltip" ) );
   props.setLook( wTranslationCreatedByIdFieldname );
   wTranslationCreatedByIdFieldname.addModifyListener( lsMod );
   FormData fdTranslationCreatedByIdFieldname = new FormData();
   fdTranslationCreatedByIdFieldname.left = new FormAttachment( 0, -margin );
   fdTranslationCreatedByIdFieldname.top = new FormAttachment( wTranslationUpdatedByIdFieldname, 2 * margin );
   fdTranslationCreatedByIdFieldname.right = new FormAttachment( 100, -margin );
   wTranslationCreatedByIdFieldname.setLayoutData( fdTranslationCreatedByIdFieldname );



   FormData fdTranslationComp = new FormData();
   fdTranslationComp.left = new FormAttachment( 0, 0 );
   fdTranslationComp.top = new FormAttachment( 0, 0 );
   fdTranslationComp.right = new FormAttachment( 100, 0 );
   fdTranslationComp.bottom = new FormAttachment( 100, 0 );
   wTranslationComp.setLayoutData( fdTranslationComp );

   wTranslationComp.layout();
   wTranslationTab.setControl( wTranslationComp );

   // /////////////////////////
   // END OF TRANSLATION TAB //
   // /////////////////////////

   FormData fdTabFolder = new FormData();
   fdTabFolder.left = new FormAttachment( 0, 0 );
   fdTabFolder.top = new FormAttachment( wStepname, margin );
   fdTabFolder.right = new FormAttachment( 100, 0 );
   fdTabFolder.bottom = new FormAttachment( 100, -50 );
   wTabFolder.setLayoutData( fdTabFolder );

   wTabFolder.setSelection( 0 );

   // ////////////////////
   // END OF TAB FOLDER //
   // ////////////////////

   // Some buttons
   wOK = new Button( shell, SWT.PUSH );
   wOK.setText( BaseMessages.getString( PKG, "System.Button.OK" ) );
   wCancel = new Button( shell, SWT.PUSH );
   wCancel.setText( BaseMessages.getString( PKG, "System.Button.Cancel" ) );

   setButtonPositions( new Button[] { wOK, wCancel }, margin, wTabFolder );

   // Add listeners
   lsCancel = new Listener() {
     public void handleEvent( Event e ) {
       cancel();
     }
   };
   lsOK = new Listener() {
     public void handleEvent( Event e ) {
       ok();
     }
   };

   wCancel.addListener( SWT.Selection, lsCancel );
   wOK.addListener( SWT.Selection, lsOK );

   lsDef = new SelectionAdapter() {
     public void widgetDefaultSelected( SelectionEvent e ) {
       ok();
     }
   };

   wStepname.addSelectionListener( lsDef );

   // Detect X or ALT-F4 or something that kills this window...
   shell.addShellListener( new ShellAdapter() {
     public void shellClosed( ShellEvent e ) {
       cancel();
     }
   } );

   // Set the shell size, based upon previous time...
   setSize();

   getData();
   input.setChanged( changed );

   shell.open();
   while ( !shell.isDisposed() ) {
     if ( !display.readAndDispatch() ) {
       display.sleep();
     }
   }
   return stepname;
 }

 /**
  * Copy information from the meta-data input to the dialog fields.
  */
 public void getData() {
   wSubDomain.setText( Const.NVL( input.getSubDomain(), "" ) );
   wUsername.setText( Const.NVL( input.getUsername(), "" ) );
   wPassword.setText( Const.NVL( input.getPassword(), "" ) );
   wToken.setSelection( input.isToken() );
   wIncomingFieldname.setText( Const.NVL( input.getIncomingFieldname(), "" ) );
   wArticleIdFieldname.setText( Const.NVL( input.getArticleIdFieldname(), "" ) );
   wArticleUrlFieldname.setText( Const.NVL( input.getArticleUrlFieldname(), "" ) );
   wArticleTitleFieldname.setText( Const.NVL( input.getArticleTitleFieldname(), "" ) );
   wArticleBodyFieldname.setText( Const.NVL( input.getArticleBodyFieldname(), "" ) );
   wLocaleFieldname.setText( Const.NVL( input.getLocaleFieldname(), "" ) );
   wSourceLocaleFieldname.setText( Const.NVL( input.getSourceLocaleFieldname(), "" ) );
   wAuthorIdFieldname.setText( Const.NVL( input.getAuthorIdFieldname(), "" ) );
   wCommentsDisabledFieldname.setText( Const.NVL( input.getCommentsDisabledFieldname(), "" ) );
   wOutdatedFieldname.setText( Const.NVL( input.getOutdatedFieldname(), "" ) );
   wLabelsFieldname.setText( Const.NVL( input.getLabelsFieldname(), "" ) );
   wDraftFieldname.setText( Const.NVL( input.getDraftFieldname(), "" ) );
   wPromotedFieldname.setText( Const.NVL( input.getPromotedFieldname(), "" ) );
   wPositionFieldname.setText( Const.NVL( input.getPositionFieldname(), "" ) );
   wVoteSumFieldname.setText( Const.NVL( input.getVoteSumFieldname(), "" ) );
   wVoteCountFieldname.setText( Const.NVL( input.getVoteCountFieldname(), "" ) );
   wSectionIdFieldname.setText( Const.NVL( input.getSectionIdFieldname(), "" ) );
   wCreatedAtFieldname.setText( Const.NVL( input.getCreatedAtFieldname(), "" ) );
   wUpdatedAtFieldname.setText( Const.NVL( input.getUpdatedAtFieldname(), "" ) );
   wTranslationIdFieldname.setText( Const.NVL( input.getTranslationIdFieldname(), "" ) );
   wTranslationUrlFieldname.setText( Const.NVL( input.getTranslationUrlFieldname(), "" ) );
   wTranslationHtmlUrlFieldname.setText( Const.NVL( input.getTranslationHtmlUrlFieldname(), "" ) );
   wTranslationSourceIdFieldname.setText( Const.NVL( input.getTranslationSourceIdFieldname(), "" ) );
   wTranslationSourceTypeFieldname.setText( Const.NVL( input.getTranslationSourceTypeFieldname(), "" ) );
   wTranslationLocaleFieldname.setText( Const.NVL( input.getTranslationLocaleFieldname(), "" ) );
   wTranslationTitleFieldname.setText( Const.NVL( input.getTranslationTitleFieldname(), "" ) );
   wTranslationBodyFieldname.setText( Const.NVL( input.getTranslationBodyFieldname(), "" ) );
   wTranslationOutdatedFieldname.setText( Const.NVL( input.getTranslationOutdatedFieldname(), "" ) );
   wTranslationDraftFieldname.setText( Const.NVL( input.getTranslationDraftFieldname(), "" ) );
   wTranslationCreatedAtFieldname.setText( Const.NVL( input.getTranslationCreatedAtFieldname(), "" ) );
   wTranslationUpdatedAtFieldname.setText( Const.NVL( input.getTranslationUpdatedAtFieldname(), "" ) );
   wTranslationUpdatedByIdFieldname.setText( Const.NVL( input.getTranslationUpdatedByIdFieldname(), "" ) );
   wTranslationCreatedByIdFieldname.setText( Const.NVL( input.getTranslationCreatedByIdFieldname(), "" ) );

   wStepname.selectAll();
   wStepname.setFocus();
 }

 private void cancel() {
   stepname = null;
   input.setChanged( changed );
   dispose();
 }

 private void ok() {

   if ( Const.isEmpty( wStepname.getText() ) ) {
     return;
   }

   // Get the information for the dialog into the input structure.
   getInfo( input );

   dispose();
 }

 private void getInfo( ZendeskInputHCArticleMeta inf ) {
   inf.setSubDomain( wSubDomain.getText() );
   inf.setUsername( wUsername.getText() );
   inf.setPassword( wPassword.getText() );
   inf.setToken( wToken.getSelection() );
   inf.setIncomingFieldname( wIncomingFieldname.getText() );
   inf.setArticleIdFieldname( wArticleIdFieldname.getText() );
   inf.setArticleUrlFieldname( wArticleUrlFieldname.getText() );
   inf.setArticleTitleFieldname( wArticleTitleFieldname.getText() );
   inf.setArticleBodyFieldname( wArticleBodyFieldname.getText() );
   inf.setLocaleFieldname( wLocaleFieldname.getText() );
   inf.setSourceLocaleFieldname( wSourceLocaleFieldname.getText() );
   inf.setAuthorIdFieldname( wAuthorIdFieldname.getText() );
   inf.setCommentsDisabledFieldname( wCommentsDisabledFieldname.getText() );
   inf.setOutdatedFieldname( wOutdatedFieldname.getText() );
   inf.setLabelsFieldname( wLabelsFieldname.getText() );
   inf.setDraftFieldname( wDraftFieldname.getText() );
   inf.setPromotedFieldname( wPromotedFieldname.getText() );
   inf.setPositionFieldname( wPositionFieldname.getText() );
   inf.setVoteSumFieldname( wVoteSumFieldname.getText() );
   inf.setVoteCountFieldname( wVoteCountFieldname.getText() );
   inf.setSectionIdFieldname( wSectionIdFieldname.getText() );
   inf.setCreatedAtFieldname( wCreatedAtFieldname.getText() );
   inf.setUpdatedAtFieldname( wUpdatedAtFieldname.getText() );
   inf.setTranslationIdFieldname( wTranslationIdFieldname.getText() );
   inf.setTranslationUrlFieldname( wTranslationUrlFieldname.getText() );
   inf.setTranslationHtmlUrlFieldname( wTranslationHtmlUrlFieldname.getText() );
   inf.setTranslationSourceIdFieldname( wTranslationSourceIdFieldname.getText() );
   inf.setTranslationSourceTypeFieldname( wTranslationSourceTypeFieldname.getText() );
   inf.setTranslationLocaleFieldname( wTranslationLocaleFieldname.getText() );
   inf.setTranslationTitleFieldname( wTranslationTitleFieldname.getText() );
   inf.setTranslationBodyFieldname( wTranslationBodyFieldname.getText() );
   inf.setTranslationOutdatedFieldname( wTranslationOutdatedFieldname.getText() );
   inf.setTranslationDraftFieldname( wTranslationDraftFieldname.getText() );
   inf.setTranslationCreatedAtFieldname( wTranslationCreatedAtFieldname.getText() );
   inf.setTranslationUpdatedAtFieldname( wTranslationUpdatedAtFieldname.getText() );
   inf.setTranslationUpdatedByIdFieldname( wTranslationUpdatedByIdFieldname.getText() );
   inf.setTranslationCreatedByIdFieldname( wTranslationCreatedByIdFieldname.getText() );
   stepname = wStepname.getText(); // return value
 }
}
