import os

from oeqa.oetest import oeRuntimeTest, skipModule
from oeqa.utils.decorators import *

class MonoCompileTest(oeRuntimeTest):

    @classmethod
    def setUpClass(cls):

        files_dir = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), "files"))

        dst = '/tmp/'
        src = os.path.join(files_dir, 'helloworld.cs')
        cls.tc.target.copy_to(src, dst)
        src = os.path.join(files_dir, 'helloworldform.cs')
        cls.tc.target.copy_to(src, dst)
        src = os.path.join(files_dir, 'helloworldgtk.cs')
        cls.tc.target.copy_to(src, dst)

    @classmethod
    def tearDownClass(cls):
        files = '/tmp/helloworld.cs /tmp/helloworld.exe /tmp/helloworldform.cs /tmp/helloworldform.exe /tmp/helloworldgtk.cs /tmp/helloworldgtk.exe'
        cls.tc.target.run('rm %s' % files)

    def test_executable_compile_and_run_cmdline(self):
        status, output = self.target.run('mcs /tmp/helloworld.cs -out:/tmp/helloworld.exe')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('mono /tmp/helloworld.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        self.assertEqual(output, 'HelloWorld', msg=msg)

    def test_executable_compile_and_run_winform(self):
        status, output = self.target.run('mcs /tmp/helloworldform.cs -out:/tmp/helloworldform.exe -r:System.Windows.Forms -r:System.Data -r:System.Drawing')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('export DISPLAY=:0; mono /tmp/helloworldform.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)

    def test_executable_compile_and_run_gtk(self):
        status, output = self.target.run('mcs /tmp/helloworldgtk.cs -out:/tmp/helloworldgtk.exe -r:System.Windows.Forms -r:System.Data -r:System.Drawing -lib:/usr/lib/mono/gtk-sharp-2.0 -r:gtk-sharp -r:glib-sharp -r:atk-sharp')
        msg = 'mcs compile failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
        status, output = self.target.run('export DISPLAY=:0; mono /tmp/helloworldgtk.exe')
        msg = 'running compiled file failed, output: %s' % output
        self.assertEqual(status, 0, msg=msg)
