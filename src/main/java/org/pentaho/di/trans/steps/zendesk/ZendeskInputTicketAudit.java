/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Pentaho : http://www.pentaho.com
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

package org.pentaho.di.trans.steps.zendesk;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.zendesk.client.v2.ZendeskResponseException;
import org.zendesk.client.v2.model.Audit;

public class ZendeskInputTicketAudit extends ZendeskInput {

  ZendeskInputTicketAuditMeta meta;
  ZendeskInputTicketAuditData data;

  private int ticketIdFieldIndex;

  public ZendeskInputTicketAudit( StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr,
      TransMeta transMeta, Trans trans ) {
    super( stepMeta, stepDataInterface, copyNr, transMeta, trans );
  }

  @Override
  public boolean init( StepMetaInterface smi, StepDataInterface sdi ) {
    if ( super.init( smi, sdi ) ) {
      meta = (ZendeskInputTicketAuditMeta) smi;
      data = (ZendeskInputTicketAuditData) sdi;
      return true;
    }
    return false;
  }

  @Override
  public boolean processRow( StepMetaInterface smi, StepDataInterface sdi ) throws KettleException {
    Object[] row = getRow();
    if ( row == null ) {
      setOutputDone();
      return false;
    }
    if ( first ) {
      first = false;
      ticketIdFieldIndex = getInputRowMeta().indexOfValue( environmentSubstitute( meta.getTicketIdFieldname() ) );
      if ( ticketIdFieldIndex < 0 ) {
        throw new KettleStepException( BaseMessages.getString( PKG, "ZendeskInputTicketAudit.Error.NoTicketIDField" ) );
      }

      if ( meta.getTicketOverviewStepMeta() != null ) {
        data.ticketOverviewOutputRowMeta = new RowMeta();
        meta.getFields( data.ticketOverviewOutputRowMeta, getStepname(), null,
            meta.getTicketOverviewStepMeta(), this, repository, metaStore );
        data.ticketOverviewOutputRowSet = findOutputRowSet( meta.getTicketOverviewStepMeta().getName() );
      }

      if ( meta.getTicketCommentsStepMeta() != null ) {
        data.ticketCommentsOutputRowMeta = new RowMeta();
        meta.getFields( data.ticketCommentsOutputRowMeta, getStepname(), null,
            meta.getTicketCommentsStepMeta(), this, repository, metaStore );
        data.ticketCommentsOutputRowSet = findOutputRowSet( meta.getTicketCommentsStepMeta().getName() );
      }

      if ( meta.getTicketCustomFieldsStepMeta() != null ) {
        data.ticketCustomFieldsOutputRowMeta = new RowMeta();
        meta.getFields( data.ticketCustomFieldsOutputRowMeta, getStepname(), null,
            meta.getTicketCustomFieldsStepMeta(), this, repository, metaStore );
        data.ticketCustomFieldsOutputRowSet = findOutputRowSet( meta.getTicketCustomFieldsStepMeta().getName() );
      }

      if ( meta.getTicketTagsStepMeta() != null ) {
        data.ticketTagsOutputRowMeta = new RowMeta();
        meta.getFields( data.ticketTagsOutputRowMeta, getStepname(), null,
          meta.getTicketTagsStepMeta(), this, repository, metaStore );
        data.ticketTagsOutputRowSet = findOutputRowSet( meta.getTicketTagsStepMeta().getName() );
      }

      if ( meta.getTicketCollaboratorsStepMeta() != null ) {
        data.ticketCollaboratorsOutputRowMeta = new RowMeta();
        meta.getFields( data.ticketCollaboratorsOutputRowMeta, getStepname(), null,
          meta.getTicketCollaboratorsStepMeta(), this, repository, metaStore );
        data.ticketCollaboratorsOutputRowSet = findOutputRowSet( meta.getTicketCollaboratorsStepMeta().getName() );
      }
    }

    // Process ticket audit
    data.newTicket();
    Long currentTicketId = getInputRowMeta().getValueMeta( ticketIdFieldIndex ).getInteger( row[ticketIdFieldIndex] );
    try {
      Iterable<Audit> ticketAudits = data.conn.getTicketAudits( currentTicketId );
      for ( Audit ticketAudit : ticketAudits ) {
        data.addAudit( ticketAudit );
      }
      outputRows();
    } catch ( ZendeskResponseException zre ) {
      if ( 404 == zre.getStatusCode() ) {
        putError( getInputRowMeta(), row, 1L, zre.toString(),
          getInputRowMeta().getValueMeta( ticketIdFieldIndex ).getName(), zre.getStatusText() );
      } else {
        logError( BaseMessages.getString( PKG, "ZendeskInput.Error.Generic", zre ) );
        setErrors( 1L );
        setOutputDone();
        return false;
      }
    }
    return true;
  }

  private void outputRows() throws KettleStepException {
    if ( data == null || data.auditSummaries == null ) {
      return;
    }

    long i = 0;
    for ( ZendeskTicketAuditHistory audit : data.auditSummaries.values() ) {
      if ( audit == null ) {
        continue;
      }

      if ( data.ticketOverviewOutputRowMeta != null && data.ticketOverviewOutputRowSet != null ) {
        Object[] ticketOverviewRow = RowDataUtil.allocateRowData( data.ticketOverviewOutputRowMeta.size() );
        ticketOverviewRow[0] = audit.ticketId;
        ticketOverviewRow[1] = audit.auditId;
        ticketOverviewRow[2] = new Long( ++i );
        ticketOverviewRow[3] = audit.createdTime;
        ticketOverviewRow[4] = audit.organizationId;
        ticketOverviewRow[5] = audit.requesterId;
        ticketOverviewRow[6] = audit.assigneeId;
        ticketOverviewRow[7] = audit.groupId;
        ticketOverviewRow[8] = audit.subject;
        ticketOverviewRow[9] = audit.status;
        ticketOverviewRow[10] = audit.priority;
        ticketOverviewRow[11] = audit.channel;
        ticketOverviewRow[12] = audit.type;
        ticketOverviewRow[13] = audit.satisfaction;
        ticketOverviewRow[14] = audit.locale;
        ticketOverviewRow[15] = audit.dueAt;
        ticketOverviewRow[16] = audit.satisfactionComment;
        ticketOverviewRow[17] = audit.formId;
        ticketOverviewRow[18] = audit.brandId;
        putRowTo( data.ticketOverviewOutputRowMeta, ticketOverviewRow, data.ticketOverviewOutputRowSet );
      }

      if ( audit.comment != null && data.ticketCommentsOutputRowMeta != null
          && data.ticketCommentsOutputRowSet != null ) {
        Object[] ticketCommentRow = RowDataUtil.allocateRowData( data.ticketCommentsOutputRowMeta.size() );
        ticketCommentRow[0] = audit.ticketId;
        ticketCommentRow[1] = audit.auditId;
        ticketCommentRow[2] = audit.comment.commentId;
        ticketCommentRow[3] = audit.comment.authorId;
        ticketCommentRow[4] = audit.comment.publicComment;
        ticketCommentRow[5] = audit.comment.commentBody;
        ticketCommentRow[6] = audit.comment.commentHTMLBody;
        ticketCommentRow[7] = audit.comment.changedToPrivate;
        putRowTo( data.ticketCommentsOutputRowMeta, ticketCommentRow, data.ticketCommentsOutputRowSet );
      }

      if ( audit.customFields != null && audit.customFields.size() > 0
          && data.ticketCustomFieldsOutputRowMeta != null && data.ticketCustomFieldsOutputRowSet != null ) {
        for ( String fieldName : audit.customFields.keySet() ) {
          Object[] customFieldRow = RowDataUtil.allocateRowData( data.ticketCustomFieldsOutputRowMeta.size() );
          customFieldRow[0] = audit.ticketId;
          customFieldRow[1] = audit.auditId;
          customFieldRow[2] = fieldName;
          customFieldRow[3] = audit.customFields.get( fieldName );
          putRowTo( data.ticketCustomFieldsOutputRowMeta, customFieldRow, data.ticketCustomFieldsOutputRowSet );
        }
      }

      if ( audit.tags != null && audit.tags.size() > 0
          && data.ticketTagsOutputRowMeta != null && data.ticketTagsOutputRowSet != null ) {
        for ( String tag : audit.tags ) {
          Object[] tagRow = RowDataUtil.allocateRowData( data.ticketTagsOutputRowMeta.size() );
          tagRow[0] = audit.ticketId;
          tagRow[1] = audit.auditId;
          tagRow[2] = tag;
          putRowTo( data.ticketTagsOutputRowMeta, tagRow, data.ticketTagsOutputRowSet );
        }
      }

      if ( audit.collaborators != null && audit.collaborators.size() > 0
          && data.ticketCollaboratorsOutputRowMeta != null && data.ticketCollaboratorsOutputRowSet != null ) {
        for ( Long collaborator : audit.collaborators ) {
          Object[] collaboratorRow = RowDataUtil.allocateRowData( data.ticketCollaboratorsOutputRowMeta.size() );
          collaboratorRow[0] = audit.ticketId;
          collaboratorRow[1] = audit.auditId;
          collaboratorRow[2] = collaborator;
          putRowTo( data.ticketCollaboratorsOutputRowMeta, collaboratorRow, data.ticketCollaboratorsOutputRowSet );
        }
      }
    }
  }
}
