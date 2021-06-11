package net.bytebuddy.utility.visitor;

import net.bytebuddy.utility.OpenedClassReader;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MetadataAwareClassVisitorTest {

    private DelegatingMetadataAwareClassVisitor classVisitor;

    @Before
    public void setUp() throws Exception {
        classVisitor = new DelegatingMetadataAwareClassVisitor();
    }

    @Test
    public void testVisit() {
        classVisitor.visit(0, 0, null, null, null, null);
        assertThat(classVisitor.nestHostVisited, is(false));
        assertThat(classVisitor.outerClassVisited, is(false));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitSource() {
        classVisitor.visitSource(null, null);
        assertThat(classVisitor.nestHostVisited, is(false));
        assertThat(classVisitor.outerClassVisited, is(false));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitNestHost() {
        classVisitor.visitNestHost(null);
        assertThat(classVisitor.nestHostVisited, is(false));
        assertThat(classVisitor.outerClassVisited, is(false));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitOuterClass() {
        classVisitor.visitOuterClass(null, null, null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(false));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitAnnotation() {
        classVisitor.visitAnnotation(null, false);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitTypeAnnotation() {
        classVisitor.visitTypeAnnotation(0, null, null, false);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitAttribute() {
        classVisitor.visitAttribute(null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(false));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitInnerClass() {
        classVisitor.visitInnerClass(null, null, null, 0);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitNestMember() {
        classVisitor.visitNestMember(null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitRecordComponent() {
        classVisitor.visitRecordComponent(null, null, null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
        assertThat(classVisitor.afterRecordComponentsVisited, is(false));
    }

    @Test
    public void testVisitPermittedSubclass() {
        classVisitor.visitPermittedSubclass(null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
        assertThat(classVisitor.afterPermittedSubclassesVisited, is(false));
    }

    @Test
    public void testVisitField() {
        classVisitor.visitField(0, null, null, null, null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
    }

    @Test
    public void testVisitMethod() {
        classVisitor.visitMethod(0, null, null, null, null);
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
    }

    @Test
    public void testVisitEnd() {
        classVisitor.visitEnd();
        assertThat(classVisitor.nestHostVisited, is(true));
        assertThat(classVisitor.outerClassVisited, is(true));
        assertThat(classVisitor.afterAttributesVisited, is(true));
        assertThat(classVisitor.afterRecordComponentsVisited, is(true));
        assertThat(classVisitor.afterPermittedSubclassesVisited, is(true));
    }

    private static class DelegatingMetadataAwareClassVisitor extends MetadataAwareClassVisitor {

        private boolean nestHostVisited, outerClassVisited, afterAttributesVisited, afterRecordComponentsVisited, afterPermittedSubclassesVisited;

        private DelegatingMetadataAwareClassVisitor() {
            super(OpenedClassReader.ASM_API, null);
        }

        @Override
        protected void onNestHost() {
            nestHostVisited = true;
        }

        @Override
        protected void onOuterType() {
            outerClassVisited = true;
        }

        @Override
        protected void onAfterAttributes() {
            afterAttributesVisited = true;
        }

        @Override
        protected void onAfterRecordComponents() {
            afterRecordComponentsVisited = true;
        }

        @Override
        protected void onAfterPermittedSubclasses() {
            afterPermittedSubclassesVisited = true;
        }
    }
}